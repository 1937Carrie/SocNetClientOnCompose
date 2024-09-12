package com.dumchykov.socialnetworkdemo.webapi.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Use this for get all users api request
 */
@Serializable
data class MultipleUserResponse(
    @SerialName("users") val users: List<ApiContact>,
)
