package com.example.testandroid.components.remoteConfig

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testandroid.cores.models.RemoteConfigKey
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

sealed class RemoteConfigStatus {
    object Normal : RemoteConfigStatus()
    object Maintenance : RemoteConfigStatus()
    data class VersionOutDated(val minimumVersion: String, val url: String) : RemoteConfigStatus()
    data class ForceUpdate(val minimumVersion: String, val url: String) : RemoteConfigStatus()
}

@HiltViewModel
class RemoteConfigDialogViewModel @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    @param:ApplicationContext private val context: Context
) : ViewModel() {

    private val _remoteConfigStatus =
        MutableStateFlow<RemoteConfigStatus>(RemoteConfigStatus.Normal)
    val remoteConfigStatus: StateFlow<RemoteConfigStatus> = _remoteConfigStatus

    init {
        viewModelScope.launch {
            fetchAndCheckConfig()
        }
    }

    private suspend fun fetchAndCheckConfig() {
        try {
            remoteConfig.fetchAndActivate().await()
            applyConfig()
        } catch (e: Exception) {
            _remoteConfigStatus.value = RemoteConfigStatus.Normal
        }
    }

    private fun applyConfig() {
        val version = remoteConfig.getString(RemoteConfigKey.ANDROID_CURRENT_VERSION.key)
        val isMaintenance = remoteConfig.getBoolean(RemoteConfigKey.ANDROID_MAINTENANCE.key)
        val storeUrl = remoteConfig.getString(RemoteConfigKey.ANDROID_STORE_URL.key)
        val isUpdateRequired = remoteConfig.getBoolean(RemoteConfigKey.ANDROID_UPDATE_REQUIRED.key)

        val (currentVersion, _) = getAppVersion()
        val isVersionOutdated = checkVersionOutdated(currentVersion, version)

        _remoteConfigStatus.value = when {
            isMaintenance -> RemoteConfigStatus.Maintenance
            isVersionOutdated && isUpdateRequired -> RemoteConfigStatus.ForceUpdate(
                version,
                storeUrl
            )

            isVersionOutdated && !isUpdateRequired -> RemoteConfigStatus.VersionOutDated(
                version,
                storeUrl
            )

            else -> RemoteConfigStatus.Normal
        }
    }

    private fun getAppVersion(): Pair<String, Long> {
        return try {
            val packageManager = context.packageManager
            val packageName = context.packageName

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val packageInfo = packageManager.getPackageInfo(
                    packageName,
                    PackageManager.PackageInfoFlags.of(0)
                )
                Pair(packageInfo.versionName ?: "Unknown", packageInfo.longVersionCode)
            } else {
                @Suppress("DEPRECATION")
                val packageInfo = packageManager.getPackageInfo(packageName, 0)
                val versionCode = @Suppress("DEPRECATION") packageInfo.versionCode.toLong()
                Pair(packageInfo.versionName ?: "Unknown", versionCode)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Pair("Unknown", -1L)
        }
    }

    private fun checkVersionOutdated(current: String, minimum: String): Boolean {
        return try {
            val currentParts = current.split(".").map { it.toInt() }
            val minimumParts = minimum.split(".").map { it.toInt() }
            val maxLength = maxOf(currentParts.size, minimumParts.size)

            for (i in 0 until maxLength) {
                val currentPart = currentParts.getOrElse(i) { 0 }
                val minimumPart = minimumParts.getOrElse(i) { 0 }
                when {
                    currentPart < minimumPart -> return true
                    currentPart > minimumPart -> return false
                }
            }
            false
        } catch (e: Exception) {
            false
        }
    }

    fun onVersionConfirm(deepLink: String) {
        val webUri = deepLink.toUri()
        val marketIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
            setPackage("com.android.vending")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(marketIntent)
        } catch (e: ActivityNotFoundException) {
            val fallbackUri =
                "https://play.google.com/store/apps/details?id=${context.packageName}".toUri()
            val webIntent = Intent(Intent.ACTION_VIEW, fallbackUri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(webIntent)
        }
    }

    fun onDismiss() {
        _remoteConfigStatus.value = RemoteConfigStatus.Normal
    }
}