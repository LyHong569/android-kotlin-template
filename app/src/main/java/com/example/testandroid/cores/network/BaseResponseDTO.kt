package com.example.testandroid.cores.network

interface BaseResponseDTO {
    val status: Boolean
    val title: String
    val message: String?
}