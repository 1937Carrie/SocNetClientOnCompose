package com.dumchykov.socialnetworkdemo.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MultipleContactResponse(
    @SerialName("contacts") val contacts: List<Contact>,
)
