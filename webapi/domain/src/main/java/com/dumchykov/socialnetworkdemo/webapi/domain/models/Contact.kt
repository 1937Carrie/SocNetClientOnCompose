package com.dumchykov.socialnetworkdemo.webapi.domain.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant

@Serializable
data class Contact(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String?,
    @SerialName("email") val email: String?,
    @SerialName("phone") val phone: String?,
    @SerialName("career") val career: String?,
    @SerialName("address") val address: String?,
    @SerialName("birthday") @Serializable(InstantSerializer::class) val birthday: Instant?,
    @SerialName("facebook") val facebook: String?,
    @SerialName("instagram") val instagram: String?,
    @SerialName("twitter") val twitter: String?,
    @SerialName("linkedin") val linkedin: String?,
    @SerialName("image") val image: String?,
    @SerialName("created_at") @Serializable(InstantSerializer::class) val created_at: Instant,
    @SerialName("updated_at") @Serializable(InstantSerializer::class) val updated_at: Instant,
)

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Instant::class)
object InstantSerializer : KSerializer<Instant> {

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString())
    }
}