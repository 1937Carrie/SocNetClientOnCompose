package com.dumchykov.socialnetworkdemo.webapi.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactResponse<T>(
    @SerialName("status") val status: String,
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("data") val data: T,
)
