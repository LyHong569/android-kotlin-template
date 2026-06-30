package com.example.testandroid.cores.firebase

import com.example.testandroid.R
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {

    @Provides
    @Singleton
    fun provideRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
//            minimumFetchIntervalInSeconds = 32000
            minimumFetchIntervalInSeconds = 30
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        return remoteConfig
    }
}