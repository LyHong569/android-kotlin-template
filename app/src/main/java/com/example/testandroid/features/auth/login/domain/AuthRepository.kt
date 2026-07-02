package com.example.testandroid.features.auth.login.domain

interface AuthRepository {
    suspend fun authLogin(username: String, password: String): AuthModel
    suspend fun authenticated(): AuthModel
    suspend fun authGoogleLogin(idToken: String): AuthModel
}