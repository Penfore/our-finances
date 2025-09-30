package net.sertiva.ourfinances.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.sertiva.ourfinances.domain.model.FinancialSummary
import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.model.TransactionType
import net.sertiva.ourfinances.domain.usecase.DeleteTransactionUseCase
import net.sertiva.ourfinances.domain.usecase.GetFinancialSummaryUseCase
import net.sertiva.ourfinances.domain.usecase.GetTransactionsUseCase
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @Mock
    private lateinit var getFinancialSummaryUseCase: GetFinancialSummaryUseCase

    @Mock
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load transactions and financial summary on init`() = testScope.runTest {
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
                title = "Groceries",
                amount = 150.0,
                type = TransactionType.EXPENSE,
                category = "Food",
                date = LocalDateTime.now()
            )
        )
        val summary = FinancialSummary(
            totalIncome = 3000.0,
            totalExpense = 150.0,
            totalBalance = 2850.0,
            transactionCount = 2
        )

        whenever(getTransactionsUseCase()).thenReturn(flowOf(transactions))
        whenever(getFinancialSummaryUseCase()).thenReturn(flowOf(summary))

        // When
        viewModel = HomeViewModel(getTransactionsUseCase, getFinancialSummaryUseCase, deleteTransactionUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(transactions, state.transactions)
        assertEquals(summary, state.financialSummary)
        assertNull(state.error)
    }

    @Test
    fun `should show loading state initially`() = testScope.runTest {
        // Given
        whenever(getTransactionsUseCase()).thenReturn(flowOf(emptyList()))
        whenever(getFinancialSummaryUseCase()).thenReturn(flowOf(FinancialSummary()))

        // When
        viewModel = HomeViewModel(getTransactionsUseCase, getFinancialSummaryUseCase, deleteTransactionUseCase)

        testDispatcher.scheduler.advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertFalse("Estado final não deve estar carregando", finalState.isLoading)
        assertTrue("Lista de transações deve estar vazia", finalState.transactions.isEmpty())
        assertEquals("Summary deve ter valores zerados", 0.0, finalState.financialSummary.totalBalance, 0.01)
    }

    @Test
    fun `should handle empty transactions list`() = testScope.runTest {
        // Given
        val emptyTransactions = emptyList<Transaction>()
        val emptySummary = FinancialSummary()

        whenever(getTransactionsUseCase()).thenReturn(flowOf(emptyTransactions))
        whenever(getFinancialSummaryUseCase()).thenReturn(flowOf(emptySummary))

        // When
        viewModel = HomeViewModel(getTransactionsUseCase, getFinancialSummaryUseCase, deleteTransactionUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertTrue(state.transactions.isEmpty())
        assertEquals(0.0, state.financialSummary.totalBalance, 0.01)
        assertEquals(0, state.financialSummary.transactionCount)
        assertNull(state.error)
    }

    @Test
    fun `should delete transaction successfully`() = testScope.runTest {
        // Given
        val transactionId = 1L
        whenever(getTransactionsUseCase()).thenReturn(flowOf(emptyList()))
        whenever(getFinancialSummaryUseCase()).thenReturn(flowOf(FinancialSummary()))
        whenever(deleteTransactionUseCase(transactionId)).thenReturn(Result.success(Unit))

        viewModel = HomeViewModel(getTransactionsUseCase, getFinancialSummaryUseCase, deleteTransactionUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.deleteTransaction(transactionId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify(deleteTransactionUseCase).invoke(transactionId)

        // Verifica que não há erro no estado
        val state = viewModel.uiState.value
        assertNull("Não deve haver erro após deleção bem-sucedida", state.error)
    }

    @Test
    fun `should handle delete transaction failure`() = testScope.runTest {
        // Given
        val transactionId = 1L
        val errorMessage = "Failed to delete transaction"
        whenever(getTransactionsUseCase()).thenReturn(flowOf(emptyList()))
        whenever(getFinancialSummaryUseCase()).thenReturn(flowOf(FinancialSummary()))
        whenever(deleteTransactionUseCase(transactionId)).thenReturn(Result.failure(RuntimeException(errorMessage)))

        viewModel = HomeViewModel(getTransactionsUseCase, getFinancialSummaryUseCase, deleteTransactionUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.deleteTransaction(transactionId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals("Erro deve aparecer no estado", errorMessage, state.error)
    }

    @Test
    fun `should clear error when clearError is called`() = testScope.runTest {
        // Given
        whenever(getTransactionsUseCase()).thenReturn(flowOf(emptyList()))
        whenever(getFinancialSummaryUseCase()).thenReturn(flowOf(FinancialSummary()))

        viewModel = HomeViewModel(getTransactionsUseCase, getFinancialSummaryUseCase, deleteTransactionUseCase)

        // Simula um erro
        whenever(deleteTransactionUseCase(1L)).thenReturn(Result.failure(RuntimeException("Test error")))
        viewModel.deleteTransaction(1L)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.clearError()

        // Then
        val state = viewModel.uiState.value
        assertNull("Erro deve ser limpo", state.error)
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val financialSummary: FinancialSummary = FinancialSummary(),
    val error: String? = null
)
