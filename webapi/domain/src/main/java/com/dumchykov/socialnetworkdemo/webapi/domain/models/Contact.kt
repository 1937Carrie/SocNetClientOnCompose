package com.dumchykov.socialnetworkdemo.webapi.domain.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

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

object CalendarSerializer : KSerializer<Calendar> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Calendar", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Calendar) {
        // Format the Calendar to ISO 8601 string
        val string = dateFormat.format(value.time)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Calendar {
        // Parse the ISO 8601 string to Date
        val string = decoder.decodeString()
        val date = dateFormat.parse(string.substringBefore('T'))

        // Convert Date back to Calendar
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }
}