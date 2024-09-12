package com.dumchykov.socialnetworkdemo.webapi.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingleUserResponse(
    @SerialName("user") val user: ApiContact,
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String,
)
