package com.example.testandroid.features.auth.login.data

import com.example.testandroid.features.auth.login.domain.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun authLogin(@Body request: LoginRequest): AuthResponseDTO

    @POST("/auth/authenticated")
    suspend fun authenticated(): AuthResponseDTO

    @POST("/auth/login-with-google")
    suspend fun authGoogleLogin(@Body request: GoogleLoginRequest): AuthResponseDTO
}