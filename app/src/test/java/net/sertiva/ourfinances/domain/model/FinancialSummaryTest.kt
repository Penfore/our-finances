package net.sertiva.ourfinances.domain.model

import org.junit.Assert.*
import org.junit.Test

class FinancialSummaryTest {

    @Test
    fun `should create financial summary with default values`() {
        // Given & When
        val summary = FinancialSummary()

        // Then
        assertEquals(0.0, summary.totalIncome, 0.01)
        assertEquals(0.0, summary.totalExpense, 0.01)
        assertEquals(0.0, summary.totalBalance, 0.01)
        assertEquals(0, summary.transactionCount)
        assertEquals(0.0, summary.netBalance, 0.01)
        assertTrue(summary.isPositiveBalance)
    }

    @Test
    fun `should calculate total balance correctly`() {
        // Given
        val income = 5000.0
        val expense = 3000.0

        // When
        val summary = FinancialSummary(
            totalIncome = income,
            totalExpense = expense,
            transactionCount = 10
        )

        // Then
        assertEquals(income, summary.totalIncome, 0.01)
        assertEquals(expense, summary.totalExpense, 0.01)
        assertEquals(2000.0, summary.totalBalance, 0.01)
        assertEquals(10, summary.transactionCount)
    }

    @Test
    fun `should calculate net balance correctly`() {
        // Given
        val income = 3000.0
        val expense = 2000.0
        val summary = FinancialSummary(
            totalIncome = income,
            totalExpense = expense
        )

        // When
        val netBalance = summary.netBalance

        // Then
        assertEquals(1000.0, netBalance, 0.01)
        assertEquals(summary.totalBalance, netBalance, 0.01)
    }

    @Test
    fun `should return positive balance when income exceeds expense`() {
        // Given
        val summary = FinancialSummary(
            totalIncome = 5000.0,
            totalExpense = 3000.0
        )

        // When & Then
        assertTrue(summary.isPositiveBalance)
        assertTrue(summary.netBalance > 0)
    }

    @Test
    fun `should return negative balance when expense exceeds income`() {
        // Given
        val summary = FinancialSummary(
            totalIncome = 2000.0,
            totalExpense = 3000.0
        )

        // When & Then
        assertFalse(summary.isPositiveBalance)
        assertTrue(summary.netBalance < 0)
        assertEquals(-1000.0, summary.netBalance, 0.01)
    }

    @Test
    fun `should return zero balance when income equals expense`() {
        // Given
        val amount = 2500.0
        val summary = FinancialSummary(
            totalIncome = amount,
            totalExpense = amount
        )

        // When & Then
        assertTrue(summary.isPositiveBalance) // Zero is considered positive
        assertEquals(0.0, summary.netBalance, 0.01)
        assertEquals(0.0, summary.totalBalance, 0.01)
    }

    @Test
    fun `should handle large amounts correctly`() {
        // Given
        val largeIncome = 999999.99
        val largeExpense = 500000.50
        val summary = FinancialSummary(
            totalIncome = largeIncome,
            totalExpense = largeExpense,
            transactionCount = 1000
        )

        // When & Then
        assertEquals(largeIncome, summary.totalIncome, 0.01)
        assertEquals(largeExpense, summary.totalExpense, 0.01)
        assertEquals(499999.49, summary.netBalance, 0.01)
        assertEquals(1000, summary.transactionCount)
        assertTrue(summary.isPositiveBalance)
    }

    @Test
    fun `should handle zero values correctly`() {
        // Given
        val summary = FinancialSummary(
            totalIncome = 0.0,
            totalExpense = 0.0,
            transactionCount = 0
        )

        // When & Then
        assertEquals(0.0, summary.totalIncome, 0.01)
        assertEquals(0.0, summary.totalExpense, 0.01)
        assertEquals(0.0, summary.netBalance, 0.01)
        assertEquals(0, summary.transactionCount)
        assertTrue(summary.isPositiveBalance)
    }
}
