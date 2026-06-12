package com.example.testandroid.features.auth.login.data

import com.example.testandroid.cores.network.BaseResponseDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDTO(
    @SerialName("token") val token: String,

    @SerialName("user") val user: UserResponseDTO,

    @SerialName("status") override val status: Boolean,
    @SerialName("title") override val title: String,
    @SerialName("message") override val message: String? = null,
) : BaseResponseDTO

@Serializable
data class UserResponseDTO(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("userInfoIdentity") val userInfoIdentity: String,
)