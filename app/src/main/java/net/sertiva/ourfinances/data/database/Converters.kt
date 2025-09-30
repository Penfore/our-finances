package net.sertiva.ourfinances.data.database

import androidx.room.TypeConverter
import net.sertiva.ourfinances.data.database.entities.TransactionType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.parse(it, formatter) }
    }

    @TypeConverter
    fun fromTransactionType(type: TransactionType): String {
        return type.name
    }

    @TypeConverter
    fun toTransactionType(typeString: String): TransactionType {
        return TransactionType.valueOf(typeString)
    }
}
