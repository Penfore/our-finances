package net.sertiva.ourfinances.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.sertiva.ourfinances.data.database.dao.TransactionDao
import net.sertiva.ourfinances.data.database.entities.TransactionEntity
import net.sertiva.ourfinances.data.database.entities.TransactionType as EntityTransactionType
import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.model.TransactionType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class TransactionRepositoryTest {

    @Mock
    private lateinit var transactionDao: TransactionDao

    private lateinit var repository: TransactionRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = TransactionRepository(transactionDao)
    }

    @Test
    fun `should get all transactions from dao`() = runTest {
        // Given
        val entities = listOf(
            TransactionEntity(
                id = 1L,
                title = "Salary",
                amount = 3000.0,
                type = EntityTransactionType.INCOME,
                category = "Salary",
                description = null,
                date = LocalDateTime.now()
            ),
            TransactionEntity(
                id = 2L,
                title = "Groceries",
                amount = 150.0,
                type = EntityTransactionType.EXPENSE,
                category = "Food",
                description = "Weekly shopping",
                date = LocalDateTime.now()
            )
        )
        whenever(transactionDao.getAllTransactions()).thenReturn(flowOf(entities))

        // When
        val result = repository.getAllTransactions().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Salary", result[0].title)
        assertEquals(3000.0, result[0].amount, 0.01)
        assertEquals(TransactionType.INCOME, result[0].type)
        assertEquals("Groceries", result[1].title)
        assertEquals(150.0, result[1].amount, 0.01)
        assertEquals(TransactionType.EXPENSE, result[1].type)
        verify(transactionDao).getAllTransactions()
    }

    @Test
    fun `should return empty list when no transactions exist`() = runTest {
        // Given
        whenever(transactionDao.getAllTransactions()).thenReturn(flowOf(emptyList()))

        // When
        val result = repository.getAllTransactions().first()

        // Then
        assertTrue(result.isEmpty())
        verify(transactionDao).getAllTransactions()
    }

    @Test
    fun `should get transaction by id from dao`() = runTest {
        // Given
        val entity = TransactionEntity(
            id = 1L,
            title = "Salary",
            amount = 3000.0,
            type = EntityTransactionType.INCOME,
            category = "Salary",
            description = null,
            date = LocalDateTime.now()
        )
        whenever(transactionDao.getTransactionById(1L)).thenReturn(entity)

        // When
        val result = repository.getTransactionById(1L)

        // Then
        assertNotNull(result)
        assertEquals("Salary", result?.title)
        assertEquals(3000.0, result?.amount)
        assertEquals(TransactionType.INCOME, result?.type)
        verify(transactionDao).getTransactionById(1L)
    }

    @Test
    fun `should return null when transaction not found by id`() = runTest {
        // Given
        whenever(transactionDao.getTransactionById(999L)).thenReturn(null)

        // When
        val result = repository.getTransactionById(999L)

        // Then
        assertNull(result)
        verify(transactionDao).getTransactionById(999L)
    }

    @Test
    fun `should get transactions by type from dao`() = runTest {
        // Given
        val incomeEntities = listOf(
            TransactionEntity(
                id = 1L,
                title = "Salary",
                amount = 3000.0,
                type = EntityTransactionType.INCOME,
                category = "Salary",
                description = null,
                date = LocalDateTime.now()
            )
        )
        whenever(transactionDao.getTransactionsByType(EntityTransactionType.INCOME))
            .thenReturn(flowOf(incomeEntities))

        // When
        val result = repository.getTransactionsByType(TransactionType.INCOME).first()

        // Then
        assertEquals(1, result.size)
        assertEquals(TransactionType.INCOME, result[0].type)
        verify(transactionDao).getTransactionsByType(EntityTransactionType.INCOME)
    }

    @Test
    fun `should get transactions by category from dao`() = runTest {
        // Given
        val category = "Food"
        val entities = listOf(
            TransactionEntity(
                id = 1L,
                title = "Groceries",
                amount = 150.0,
                type = EntityTransactionType.EXPENSE,
                category = category,
                description = null,
                date = LocalDateTime.now()
            )
        )
        whenever(transactionDao.getTransactionsByCategory(category)).thenReturn(flowOf(entities))

        // When
        val result = repository.getTransactionsByCategory(category).first()

        // Then
        assertEquals(1, result.size)
        assertEquals(category, result[0].category)
        verify(transactionDao).getTransactionsByCategory(category)
    }

    @Test
    fun `should get transactions by date range from dao`() = runTest {
        // Given
        val startDate = LocalDateTime.of(2025, 1, 1, 0, 0)
        val endDate = LocalDateTime.of(2025, 1, 31, 23, 59)
        val entities = listOf(
            TransactionEntity(
                id = 1L,
                title = "January Expense",
                amount = 100.0,
                type = EntityTransactionType.EXPENSE,
                category = "Other",
                description = null,
                date = LocalDateTime.of(2025, 1, 15, 12, 0)
            )
        )
        whenever(transactionDao.getTransactionsByDateRange(startDate, endDate))
            .thenReturn(flowOf(entities))

        // When
        val result = repository.getTransactionsByDateRange(startDate, endDate).first()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0].date.isAfter(startDate) || result[0].date.isEqual(startDate))
        assertTrue(result[0].date.isBefore(endDate) || result[0].date.isEqual(endDate))
        verify(transactionDao).getTransactionsByDateRange(startDate, endDate)
    }

    @Test
    fun `should insert transaction through dao`() = runTest {
        // Given
        val transaction = Transaction(
            title = "Test Transaction",
            amount = 100.0,
            type = TransactionType.EXPENSE,
            category = "Test",
            description = "Test description",
            date = LocalDateTime.now()
        )

        // When
        repository.insertTransaction(transaction)

        // Then
        verify(transactionDao).insertTransaction(any())
    }

    @Test
    fun `should delete transaction by id through dao`() = runTest {
        // Given
        val transactionId = 1L

        // When
        repository.deleteTransactionById(transactionId)

        // Then
        verify(transactionDao).deleteTransactionById(transactionId)
    }

    @Test
    fun `should calculate financial summary correctly`() = runTest {
        // Given
        val entities = listOf(
            TransactionEntity(
                id = 1L,
                title = "Salary",
                amount = 3000.0,
                type = EntityTransactionType.INCOME,
                category = "Salary",
                description = null,
                date = LocalDateTime.now()
            ),
            TransactionEntity(
                id = 2L,
                title = "Bonus",
                amount = 500.0,
                type = EntityTransactionType.INCOME,
                category = "Bonus",
                description = null,
                date = LocalDateTime.now()
            ),
            TransactionEntity(
                id = 3L,
                title = "Rent",
                amount = 1200.0,
                type = EntityTransactionType.EXPENSE,
                category = "Housing",
                description = null,
                date = LocalDateTime.now()
            )
        )
        whenever(transactionDao.getAllTransactions()).thenReturn(flowOf(entities))

        // When
        val result = repository.getFinancialSummary().first()

        // Then
        assertEquals(3500.0, result.totalIncome, 0.01) // 3000 + 500
        assertEquals(1200.0, result.totalExpense, 0.01)
        assertEquals(2300.0, result.totalBalance, 0.01) // 3500 - 1200
        assertEquals(3, result.transactionCount)
        assertTrue(result.isPositiveBalance)
    }

    // Helper method to avoid compilation issues
    private fun any(): TransactionEntity = org.mockito.kotlin.any()
}
