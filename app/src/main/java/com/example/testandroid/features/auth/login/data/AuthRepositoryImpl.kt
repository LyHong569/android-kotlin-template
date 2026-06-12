package com.example.testandroid.features.auth.login.data

import com.example.testandroid.features.auth.login.domain.AuthModel
import com.example.testandroid.features.auth.login.domain.AuthRepository
import com.example.testandroid.features.auth.login.domain.LoginRequest
import com.example.testandroid.features.auth.login.domain.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRepository {
    private fun convertResponse(res: AuthResponseDTO): AuthModel {
        return AuthModel(
            message = res.message,
            title = res.title,
            status = res.status,
            token = res.token,
            user = UserModel(
                id = res.user.id,
                email = res.user.email,
                userInfoIdentity = res.user.userInfoIdentity
            )
        )
    }

    override suspend fun authLogin(username: String, password: String): AuthModel {
        val res = authService.authLogin(LoginRequest(username, password))
        return convertResponse(res)
    }

    override suspend fun authenticated(): AuthModel {
        val res = authService.authenticated()
        return convertResponse(res)
    }
}