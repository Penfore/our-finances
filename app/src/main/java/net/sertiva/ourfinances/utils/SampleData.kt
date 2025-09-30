package net.sertiva.ourfinances.utils

import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.model.TransactionType
import java.time.LocalDateTime

object SampleData {

    fun getSampleTransactions(): List<Transaction> {
        return listOf(
            Transaction(
                id = 1,
                title = "Salary",
                amount = 5000.0,
                category = "Work",
                type = TransactionType.INCOME,
                date = LocalDateTime.now().minusDays(1),
                description = "Monthly salary payment"
            ),
            Transaction(
                id = 2,
                title = "Grocery Shopping",
                amount = 150.0,
                category = "Food",
                type = TransactionType.EXPENSE,
                date = LocalDateTime.now().minusDays(2),
                description = "Weekly grocery shopping"
            ),
            Transaction(
                id = 3,
                title = "Freelance Project",
                amount = 800.0,
                category = "Work",
                type = TransactionType.INCOME,
                date = LocalDateTime.now().minusDays(3),
                description = "Web development project"
            ),
            Transaction(
                id = 4,
                title = "Gas Station",
                amount = 75.0,
                category = "Transportation",
                type = TransactionType.EXPENSE,
                date = LocalDateTime.now().minusDays(4),
                description = "Fuel for car"
            ),
            Transaction(
                id = 5,
                title = "Coffee Shop",
                amount = 25.0,
                category = "Food",
                type = TransactionType.EXPENSE,
                date = LocalDateTime.now().minusDays(5),
                description = "Meeting with client"
            )
        )
    }

    val sampleCategories = listOf(
        "Work",
        "Food",
        "Transportation",
        "Entertainment",
        "Healthcare",
        "Shopping",
        "Bills",
        "Education",
        "Travel",
        "Other"
    )
}
