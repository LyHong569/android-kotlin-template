package com.example.testandroid.cores.models

interface BaseResponse {
    val message: String?
    val title: String
    val status: Boolean
}

data class ExampleResponse(
    override val message: String? = null,
    override val title: String,
    override val status: Boolean,
    // add data class here
    val example: Nothing
) : BaseResponse