package com.example.testandroid.features.auth.login.data

import com.example.testandroid.cores.network.BaseResponseDTO
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDTO(
    @SerializedName("token") val token: String,

    @SerializedName("user") val user: UserResponseDTO,

    @SerializedName("status") override val status: Boolean,
    @SerializedName("title") override val title: String,
    @SerializedName("message") override val message: String? = null,
) : BaseResponseDTO

@Serializable
data class UserResponseDTO(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("userInfoIdentity") val userInfoIdentity: String,
)

@Serializable
data class GoogleLoginRequest(
    @SerializedName("id_token") val idToken: String
)