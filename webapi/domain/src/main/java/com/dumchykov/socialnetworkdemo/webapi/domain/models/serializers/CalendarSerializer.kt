package com.dumchykov.socialnetworkdemo.webapi.domain.models.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object CalendarSerializer : KSerializer<Calendar> {
    private val dateFormat: SimpleDateFormat
        get() {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return simpleDateFormat
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