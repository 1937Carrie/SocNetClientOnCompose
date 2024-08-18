package com.dumchykov.socialnetworkdemo.webapi.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class Contact(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String?,
    @SerialName("career") val career: String?,
    @SerialName("address") val address: String?,
    @SerialName("birthday") @Serializable(InstantSerializer::class) val birthday: LocalDate?,
    @SerialName("facebook") val facebook: String?,
    @SerialName("instagram") val instagram: String?,
    @SerialName("twitter") val twitter: String?,
    @SerialName("linkedin") val linkedin: String?,
    @SerialName("image") val image: String?,
    @SerialName("created_at") @Serializable(InstantSerializer::class) val created_at: LocalDate,
    @SerialName("updated_at") @Serializable(InstantSerializer::class) val updated_at: LocalDate,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDate::class)
object InstantSerializer : KSerializer<LocalDate> {

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }
}