package com.example.testandroid.cores.managers

import com.example.testandroid.features.auth.login.domain.AuthRepository
import com.example.testandroid.features.auth.login.domain.AuthState
import dagger.Lazy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val tokenManager: TokenManager,
    private val authRepository: Lazy<AuthRepository>,
) {

    private val _validationState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _validationState.asStateFlow()

    suspend fun validateSession() {
        try {
            val token = tokenManager.getAccessToken()
            if (token.isNullOrBlank()) {
                _validationState.value = AuthState.Unauthenticated
                return
            }

            val authRes = authRepository.get().authenticated()
            if (authRes.status) {
                _validationState.value = AuthState.Authenticated
            } else {
                _validationState.value = AuthState.Unauthenticated
            }
        } catch (e: HttpException) {
            tokenManager.clearTokens()
            _validationState.value = AuthState.Unauthenticated
        } catch (e: Exception) {
            _validationState.value = AuthState.Unauthenticated
        }
    }

    suspend fun login(token: String) {
        tokenManager.saveAccessToken(token)
        _validationState.value = AuthState.Authenticated
    }

    suspend fun logout() {
        tokenManager.clearTokens()
        _validationState.value = AuthState.Unauthenticated
    }
}