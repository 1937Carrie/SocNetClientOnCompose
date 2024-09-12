package com.dumchykov.socialnetworkdemo.webapi.domain.models

import com.dumchykov.data.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.serializers.CalendarSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Calendar

@Serializable
data class ApiContact(
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
    @SerialName("created_at") @Serializable(CalendarSerializer::class) val createdAt: Calendar,
    @SerialName("updated_at") @Serializable(CalendarSerializer::class) val updatedAt: Calendar,
)

fun ApiContact.toContact(): Contact {
    return Contact(
        id = id,
        name = name.toString(),
        email = email.toString(),
        phone = phone.toString(),
        career = career.toString(),
        address = address.toString(),
        birthday = birthday,
        facebook = facebook.toString(),
        instagram = instagram.toString(),
        twitter = twitter.toString(),
        linkedin = linkedin.toString(),
        image = image.toString(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Contact.toApiContact(): ApiContact {
    return ApiContact(
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