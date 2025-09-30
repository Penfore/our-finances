package net.sertiva.ourfinances.data.database.entities

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime

class TransactionEntityTest {

    @Test
    fun `should create transaction entity with all fields`() {
        // Given
        val title = "Grocery Shopping"
        val amount = 150.0
        val category = "Food"
        val type = TransactionType.EXPENSE
        val date = LocalDateTime.now()
        val description = "Weekly groceries"

        // When
        val entity = TransactionEntity(
            id = 1L,
            title = title,
            amount = amount,
            category = category,
            type = type,
            date = date,
            description = description
        )

        // Then
        assertEquals(1L, entity.id)
        assertEquals(title, entity.title)
        assertEquals(amount, entity.amount, 0.01)
        assertEquals(category, entity.category)
        assertEquals(type, entity.type)
        assertEquals(date, entity.date)
        assertEquals(description, entity.description)
    }

    @Test
    fun `should create transaction entity with default id`() {
        // Given & When
        val entity = TransactionEntity(
            title = "Test Transaction",
            amount = 100.0,
            category = "Test",
            type = TransactionType.INCOME,
            date = LocalDateTime.now()
        )

        // Then
        assertEquals(0L, entity.id)
    }

    @Test
    fun `should create transaction entity with null description`() {
        // Given & When
        val entity = TransactionEntity(
            title = "Test Transaction",
            amount = 100.0,
            category = "Test",
            type = TransactionType.INCOME,
            date = LocalDateTime.now(),
            description = null
        )

        // Then
        assertNull(entity.description)
    }

    @Test
    fun `should handle large amounts correctly`() {
        // Given
        val largeAmount = 999999.99
        val entity = TransactionEntity(
            title = "Large Transaction",
            amount = largeAmount,
            category = "Investment",
            type = TransactionType.INCOME,
            date = LocalDateTime.now()
        )

        // When & Then
        assertEquals(largeAmount, entity.amount, 0.01)
    }

    @Test
    fun `should handle zero amount`() {
        // Given
        val entity = TransactionEntity(
            title = "Zero Transaction",
            amount = 0.0,
            category = "Other",
            type = TransactionType.EXPENSE,
            date = LocalDateTime.now()
        )

        // When & Then
        assertEquals(0.0, entity.amount, 0.01)
    }

    @Test
    fun `should support both transaction types`() {
        // Given
        val incomeEntity = TransactionEntity(
            title = "Income",
            amount = 100.0,
            category = "Test",
            type = TransactionType.INCOME,
            date = LocalDateTime.now()
        )
        val expenseEntity = TransactionEntity(
            title = "Expense",
            amount = 100.0,
            category = "Test",
            type = TransactionType.EXPENSE,
            date = LocalDateTime.now()
        )

        // When & Then
        assertEquals(TransactionType.INCOME, incomeEntity.type)
        assertEquals(TransactionType.EXPENSE, expenseEntity.type)
    }

    @Test
    fun `should handle unicode characters in strings`() {
        // Given
        val entity = TransactionEntity(
            title = "Café e Açúcar",
            amount = 25.50,
            category = "Alimentação",
            type = TransactionType.EXPENSE,
            date = LocalDateTime.now(),
            description = "Compra no mercado próximo"
        )

        // When & Then
        assertEquals("Café e Açúcar", entity.title)
        assertEquals("Alimentação", entity.category)
        assertEquals("Compra no mercado próximo", entity.description)
    }
}
