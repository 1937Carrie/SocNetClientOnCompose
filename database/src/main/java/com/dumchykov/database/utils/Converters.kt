package com.dumchykov.database.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class Converters {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    @TypeConverter
    fun fromTimestamp(value: String?): Calendar? {
        return value?.let { timeStamp ->
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(timeStamp.substringBefore('T')) ?: return null
            return calendar
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Calendar?): String? {
        return date?.let {
            return dateFormat.format(it.time)
        }
    }
}