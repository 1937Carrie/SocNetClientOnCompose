package com.dumchykov.socialnetworkdemo.webapi.domain.models

import com.dumchykov.socialnetworkdemo.webapi.domain.models.serializers.CalendarSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Calendar

@Serializable
data class Contact(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String?,
    @SerialName("career") val career: String?,
    @SerialName("address") val address: String?,
    @SerialName("birthday") @Serializable(CalendarSerializer::class) val birthday: Calendar?,
    @SerialName("facebook") val facebook: String?,
    @SerialName("instagram") val instagram: String?,
    @SerialName("twitter") val twitter: String?,
    @SerialName("linkedin") val linkedin: String?,
    @SerialName("image") val image: String?,
    @SerialName("created_at") @Serializable(CalendarSerializer::class) val created_at: Calendar,
    @SerialName("updated_at") @Serializable(CalendarSerializer::class) val updated_at: Calendar,
)