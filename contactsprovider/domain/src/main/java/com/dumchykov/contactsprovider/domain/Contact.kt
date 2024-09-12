package com.dumchykov.contactsprovider.domain

import com.dumchykov.socialnetworkdemo.webapi.domain.models.serializers.CalendarSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Calendar

@Serializable
data class Contact(
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("phone") val phone: String = "",
    @SerialName("career") val career: String = "",
    @SerialName("address") val address: String = "",
    @SerialName("birthday") @Serializable(CalendarSerializer::class) val birthday: Calendar? = null,
    @SerialName("facebook") val facebook: String = "",
    @SerialName("instagram") val instagram: String = "",
    @SerialName("twitter") val twitter: String = "",
    @SerialName("linkedin") val linkedin: String = "",
    @SerialName("image") val image: String? = null,
    @SerialName("created_at") @Serializable(CalendarSerializer::class) val createdAt: Calendar = Calendar.getInstance(),
    @SerialName("updated_at") @Serializable(CalendarSerializer::class) val updatedAt: Calendar = Calendar.getInstance(),
)

fun Contact.toDomainContact(): com.dumchykov.data.Contact {
    return com.dumchykov.data.Contact(
        id = id,
        name = name,
        email = email,
        phone = phone,
        career = career,
        address = address,
        birthday = birthday,
        facebook = facebook,
        instagram = instagram,
        twitter = twitter,
        linkedin = linkedin,
        image = image,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}