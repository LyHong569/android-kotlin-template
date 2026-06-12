package com.example.testandroid.features.auth.login.data

import com.example.testandroid.features.auth.login.domain.AuthModel
import com.example.testandroid.features.auth.login.domain.UserModel

fun AuthResponseDTO.toDomain() = AuthModel(
    token = token,
    user = user.toDomain(),
    message = message,
    status = status,
    title = title
)

fun UserResponseDTO.toDomain() = UserModel(
    id = id,
    email = email,
    userInfoIdentity = userInfoIdentity
)