package com.dumchykov.socialnetworkdemo.webapi.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultipleContactResponse(
    @SerialName("contacts") val apiContacts: List<ApiContact>,
)
