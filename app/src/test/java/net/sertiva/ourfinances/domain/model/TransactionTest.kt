package net.sertiva.ourfinances.domain.model

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime

class TransactionTest {

    @Test
    fun `should create transaction with all required fields`() {
        // Given
        val title = "Grocery Shopping"
        val amount = 150.0
        val type = TransactionType.EXPENSE
        val category = "Food"
        val description = "Weekly groceries"
        val date = LocalDateTime.now()

        // When
        val transaction = Transaction(
            id = 1L,
            title = title,
            amount = amount,
            type = type,
            category = category,
            description = description,
            date = date
        )

        // Then
        assertEquals(1L, transaction.id)
        assertEquals(title, transaction.title)
        assertEquals(amount, transaction.amount, 0.01)
        assertEquals(type, transaction.type)
        assertEquals(category, transaction.category)
        assertEquals(description, transaction.description)
        assertEquals(date, transaction.date)
    }

    @Test
    fun `should format amount correctly for income transaction`() {
        // Given
        val amount = 1000.0
        val transaction = Transaction(
            title = "Salary",
            amount = amount,
            type = TransactionType.INCOME,
            category = "Salary",
            date = LocalDateTime.now()
        )

        // When
        val formattedAmount = transaction.formattedAmount

        // Then
        assertEquals(amount, formattedAmount, 0.01)
        assertTrue("Income should be positive", formattedAmount > 0)
    }

    @Test
    fun `should format amount correctly for expense transaction`() {
        // Given
        val amount = 150.0
        val transaction = Transaction(
            title = "Grocery",
            amount = amount,
            type = TransactionType.EXPENSE,
            category = "Food",
            date = LocalDateTime.now()
        )

        // When
        val formattedAmount = transaction.formattedAmount

        // Then
        assertEquals(-amount, formattedAmount, 0.01)
        assertTrue("Expense should be negative", formattedAmount < 0)
    }

    @Test
    fun `should handle zero amount correctly`() {
        // Given
        val amount = 0.0
        val incomeTransaction = Transaction(
            title = "Zero Income",
            amount = amount,
            type = TransactionType.INCOME,
            category = "Other",
            date = LocalDateTime.now()
        )
        val expenseTransaction = Transaction(
            title = "Zero Expense",
            amount = amount,
            type = TransactionType.EXPENSE,
            category = "Other",
            date = LocalDateTime.now()
        )

        // When & Then
        assertEquals(0.0, incomeTransaction.formattedAmount, 0.01)
        assertEquals(0.0, expenseTransaction.formattedAmount, 0.01)
    }

    @Test
    fun `should handle large amounts correctly`() {
        // Given
        val largeAmount = 999999.99
        val transaction = Transaction(
            title = "Large Transaction",
            amount = largeAmount,
            type = TransactionType.INCOME,
            category = "Investment",
            date = LocalDateTime.now()
        )

        // When
        val formattedAmount = transaction.formattedAmount

        // Then
        assertEquals(largeAmount, formattedAmount, 0.01)
    }

    @Test
    fun `should create transaction with default id when not specified`() {
        // Given & When
        val transaction = Transaction(
            title = "Test Transaction",
            amount = 100.0,
            type = TransactionType.INCOME,
            category = "Test",
            date = LocalDateTime.now()
        )

        // Then
        assertEquals(0L, transaction.id)
    }

    @Test
    fun `should create transaction with null description when not specified`() {
        // Given & When
        val transaction = Transaction(
            title = "Test Transaction",
            amount = 100.0,
            type = TransactionType.INCOME,
            category = "Test",
            date = LocalDateTime.now()
        )

        // Then
        assertNull(transaction.description)
    }
}
