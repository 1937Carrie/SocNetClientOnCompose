package com.stanislavdumchykov.socialnetworkclient.domain.model

data class ServerResponse<T>(
    val status: String,
    val code: Int,
    val message: String,
    val `data`: T
)