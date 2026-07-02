package com.example.testandroid.cores.managers

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.testandroid.BuildConfig
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthManager @Inject constructor(
    private val credentialManager: CredentialManager
) {
    suspend fun signIn(activityContext: Context): GoogleIdTokenCredential {
        val rawNonce = generateNonce()
        val hashedNonce = hashNonce(rawNonce)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(WEB_CLIENT_ID)
            .setNonce(hashedNonce)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result: GetCredentialResponse = credentialManager.getCredential(
            request = request,
            context = activityContext
        )

        return GoogleIdTokenCredential.createFrom(result.credential.data)
    }

    private fun generateNonce(): String {
        val bytes = ByteArray(32)
        SecureRandom().nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    private fun hashNonce(nonce: String): String {
        val bytes = nonce.toByteArray()
        val digest = java.security.MessageDigest.getInstance("SHA-256").digest(bytes)
        return digest.joinToString("") { "%02x".format(it) }
    }

    suspend fun signOut() {
        credentialManager.clearCredentialState(
            androidx.credentials.ClearCredentialStateRequest()
        )
    }

    companion object {
        const val WEB_CLIENT_ID = BuildConfig.GOOGLE_WEB_CLIENT_ID
    }
}