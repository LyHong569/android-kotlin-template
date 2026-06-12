package com.example.testandroid.cores.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponseDTO(
    @SerialName("status")
    open val status: Boolean,

    @SerialName("title")
    open val title: String,

    @SerialName("message")
    open val message: String? = null,
)