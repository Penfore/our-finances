package net.sertiva.ourfinances.domain.usecase

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.sertiva.ourfinances.data.repository.TransactionRepository
import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.model.TransactionType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class GetFinancialSummaryUseCaseTest {

    @Mock
    private lateinit var repository: TransactionRepository

    private lateinit var useCase: GetFinancialSummaryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = GetFinancialSummaryUseCase(repository)
    }

    @Test
    fun `should calculate financial summary with mixed transactions`() = runTest {
        // Given
        val transactions = listOf(
            Transaction(
                id = 1L,
                title = "Salary",
                amount = 3000.0,
                type = TransactionType.INCOME,
                category = "Salary",
                date = LocalDateTime.now()
            ),
            Transaction(
                id = 2L,
                title = "Bonus",
                amount = 500.0,
                type = TransactionType.INCOME,
                category = "Bonus",
                date = LocalDateTime.now()
            ),
            Transaction(
                id = 3L,
                title = "Groceries",
                amount = 200.0,
                type = TransactionType.EXPENSE,
                category = "Food",
                date = LocalDateTime.now()
            ),
            Transaction(
                id = 4L,
                title = "Gas",
                amount = 80.0,
                type = TransactionType.EXPENSE,
                category = "Transport",
                date = LocalDateTime.now()
            )
        )
        whenever(repository.getAllTransactions()).thenReturn(flowOf(transactions))

        // When
        val result = useCase().first()

        // Then
        assertEquals(3500.0, result.totalIncome, 0.01) // 3000 + 500
        assertEquals(280.0, result.totalExpense, 0.01) // 200 + 80
        assertEquals(3220.0, result.totalBalance, 0.01) // 3500 - 280
        assertEquals(4, result.transactionCount)
        assertTrue(result.isPositiveBalance)
    }

    @Test
    fun `should return empty summary when no transactions exist`() = runTest {
        // Given
        whenever(repository.getAllTransactions()).thenReturn(flowOf(emptyList()))

        // When
        val result = useCase().first()

        // Then
        assertEquals(0.0, result.totalIncome, 0.01)
        assertEquals(0.0, result.totalExpense, 0.01)
        assertEquals(0.0, result.totalBalance, 0.01)
        assertEquals(0, result.transactionCount)
        assertTrue(result.isPositiveBalance)
    }

    @Test
    fun `should calculate summary with only income transactions`() = runTest {
        // Given
        val transactions = listOf(
            Transaction(
                id = 1L,
                title = "Salary",
                amount = 3000.0,
                type = TransactionType.INCOME,
                category = "Salary",
                date = LocalDateTime.now()
            ),
            Transaction(
                id = 2L,
                title = "Freelance",
                amount = 800.0,
                type = TransactionType.INCOME,
                category = "Work",
                date = LocalDateTime.now()
            )
        )
        whenever(repository.getAllTransactions()).thenReturn(flowOf(transactions))

        // When
        val result = useCase().first()

        // Then
        assertEquals(3800.0, result.totalIncome, 0.01)
        assertEquals(0.0, result.totalExpense, 0.01)
        assertEquals(3800.0, result.totalBalance, 0.01)
        assertEquals(2, result.transactionCount)
        assertTrue(result.isPositiveBalance)
    }

    @Test
    fun `should calculate summary with only expense transactions`() = runTest {
        // Given
        val transactions = listOf(
            Transaction(
                id = 1L,
                title = "Rent",
                amount = 1200.0,
                type = TransactionType.EXPENSE,
                category = "Housing",
                date = LocalDateTime.now()
            ),
            Transaction(
                id = 2L,
                title = "Utilities",
                amount = 150.0,
                type = TransactionType.EXPENSE,
                category = "Bills",
                date = LocalDateTime.now()
            )
        )
        whenever(repository.getAllTransactions()).thenReturn(flowOf(transactions))

        // When
        val result = useCase().first()

        // Then
        assertEquals(0.0, result.totalIncome, 0.01)
        assertEquals(1350.0, result.totalExpense, 0.01)
        assertEquals(-1350.0, result.totalBalance, 0.01)
        assertEquals(2, result.transactionCount)
        assertFalse(result.isPositiveBalance)
    }

    @Test
    fun `should handle large amounts correctly`() = runTest {
        // Given
        val transactions = listOf(
            Transaction(
                id = 1L,
                title = "Large Income",
                amount = 999999.99,
                type = TransactionType.INCOME,
                category = "Investment",
                date = LocalDateTime.now()
            ),
            Transaction(
                id = 2L,
                title = "Large Expense",
                amount = 500000.50,
                type = TransactionType.EXPENSE,
                category = "Investment",
                date = LocalDateTime.now()
            )
        )
        whenever(repository.getAllTransactions()).thenReturn(flowOf(transactions))

        // When
        val result = useCase().first()

        // Then
        assertEquals(999999.99, result.totalIncome, 0.01)
        assertEquals(500000.50, result.totalExpense, 0.01)
        assertEquals(499999.49, result.totalBalance, 0.01)
        assertEquals(2, result.transactionCount)
        assertTrue(result.isPositiveBalance)
    }
}
