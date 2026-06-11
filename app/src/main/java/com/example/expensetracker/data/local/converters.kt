// data/local/converters.kt
package com.example.expensetracker.data.local

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Room TypeConverters
 * Tells Room how to convert complex types (LocalDateTime)
 * to/from SQLite compatible types (String/Long)
 */
class Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it, formatter) }
    }
}