package com.dumchykov.socialnetworkdemo.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactId(
    @SerialName("contactId") val contactId: Int,
)
