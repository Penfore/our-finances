package net.sertiva.ourfinances.domain.model

import java.time.LocalDateTime

data class Transaction(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val type: TransactionType,
    val category: String,
    val description: String? = null,
    val date: LocalDateTime
) {
    val formattedAmount: Double
        get() = if (type == TransactionType.INCOME) amount else -amount
}

enum class TransactionType {
    INCOME,
    EXPENSE
}
