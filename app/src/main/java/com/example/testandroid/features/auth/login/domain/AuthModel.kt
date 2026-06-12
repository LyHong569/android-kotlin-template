package com.example.testandroid.features.auth.login.domain

import com.example.testandroid.cores.network.BaseResponse

data class AuthModel(
    override val message: String? = null,
    override val title: String,
    override val status: Boolean,
    val token: String,
    val user: UserModel
) : BaseResponse

data class UserModel(
    val id: String,
    val email: String,
    val userInfoIdentity: String
)

data class LoginRequest(
    val username: String,
    val password: String
)

sealed interface AuthState {
    data object Loading : AuthState
    data object Authenticated : AuthState
    data object Unauthenticated : AuthState
}
