package com.example.testandroid.cores.network

import com.example.testandroid.cores.datastores.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    private val API_KEY = ""
    private val API_SECRET_KEY = ""
    private val API_KEY_VALUE = ""
    private val API_SECRET_KEY_VALUE = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(API_KEY, API_SECRET_KEY)
            .addHeader(API_KEY_VALUE, API_SECRET_KEY_VALUE)
            .addHeader("Authorization", "Bearer")
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