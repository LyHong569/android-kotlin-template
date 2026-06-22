package com.example.testandroid.cores.network

import com.example.testandroid.BuildConfig
import com.example.testandroid.cores.managers.SessionManager
import com.example.testandroid.cores.managers.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {

    private val apiKey = BuildConfig.API_KEY
    private val apiSecretKey = BuildConfig.API_SECRET_KEY
    private val apiKeyValue = BuildConfig.API_KEY_VALUE
    private val apiSecretKeyValue = BuildConfig.API_SECRET_KEY_VALUE

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { tokenManager.getAccessToken() }

        val request = chain.request().newBuilder()
            .addHeader(apiKey, apiKeyValue)
            .addHeader(apiSecretKey, apiSecretKeyValue)
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("Accept", "application/json")
            .build()

        val response = chain.proceed(request)

        if (response.code == 401) {
            // Clear session
            return response
        }

        return response
    }
}

class AuthAuthenticator @Inject constructor(
    private val sessionManager: SessionManager
) : Authenticator {
    companion object {
        private val EXCLUDED_ROUTES = listOf(
            "/auth/login",
        )
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val url = response.request.url.encodedPath

        if (EXCLUDED_ROUTES.any { url.contains(it) }) {
            return null
        }

        runBlocking { sessionManager.logout() }
        return null
    }
}