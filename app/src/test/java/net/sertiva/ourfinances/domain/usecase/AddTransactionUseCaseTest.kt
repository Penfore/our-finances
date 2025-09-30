package net.sertiva.ourfinances.domain.usecase

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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class AddTransactionUseCaseTest {

    @Mock
    private lateinit var repository: TransactionRepository

    private lateinit var useCase: AddTransactionUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = AddTransactionUseCase(repository)
    }

    @Test
    fun `should add transaction successfully`() = runTest {
        // Given
        val transaction = Transaction(
            title = "Test Transaction",
            amount = 100.0,
            type = TransactionType.INCOME,
            category = "Test",
            date = LocalDateTime.now()
        )

        // When
        val result = useCase(transaction)

        // Then
        assertTrue(result.isSuccess)
        verify(repository).insertTransaction(transaction)
    }

    @Test
    fun `should return failure when repository throws exception`() = runTest {
        // Given
        val transaction = Transaction(
            title = "Test Transaction",
            amount = 100.0,
            type = TransactionType.INCOME,
            category = "Test",
            date = LocalDateTime.now()
        )
        val exception = RuntimeException("Database error")
        whenever(repository.insertTransaction(transaction)).thenThrow(exception)

        // When
        val result = useCase(transaction)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `should handle large amount transaction`() = runTest {
        // Given
        val transaction = Transaction(
            title = "Large Transaction",
            amount = 999999.99,
            type = TransactionType.EXPENSE,
            category = "Investment",
            date = LocalDateTime.now()
        )

        // When
        val result = useCase(transaction)

        // Then
        assertTrue(result.isSuccess)
        verify(repository).insertTransaction(transaction)
    }

    @Test
    fun `should handle zero amount transaction`() = runTest {
        // Given
        val transaction = Transaction(
            title = "Zero Transaction",
            amount = 0.0,
            type = TransactionType.INCOME,
            category = "Other",
            date = LocalDateTime.now()
        )

        // When
        val result = useCase(transaction)

        // Then
        assertTrue(result.isSuccess)
        verify(repository).insertTransaction(transaction)
    }
}
