package com.dumchykov.database.utils

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class Converters {
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    /**
     * Converts [String] of "dd.MM.yyyy" date format to [Calendar]
     */
    @TypeConverter
    fun fromTimestamp(value: String?): Calendar? {
        return value?.let { timeStamp ->
            val calendar = Calendar.getInstance()
            calendar.time = dateFormat.parse(timeStamp.substringBefore('T')) ?: return null
            return calendar
        }
    }

    /**
     * Converts [Calendar] to a [String] representation, in "dd.MM.yyyy" date format
     */
    @TypeConverter
    fun dateToTimestamp(date: Calendar?): String? {
        return date?.let {
            return dateFormat.format(it.time)
        }
    }

    /**
     * Converts [List] of [Int] to a comma-separated [String]
     */
    @TypeConverter
    fun fromIntList(value: List<Int>?): String? {
        return value?.joinToString(",")
    }

    /**
     * Converts [String] to a [List] of [Int]
     */
    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        return value?.split(",")?.map { it.toInt() }
    }
}