package net.sertiva.ourfinances.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val amount: Double,
    val category: String,
    val type: TransactionType,
    val date: LocalDateTime,
    val description: String? = null
)

enum class TransactionType {
    INCOME,
    EXPENSE
}
