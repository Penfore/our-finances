package net.sertiva.ourfinances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import net.sertiva.ourfinances.domain.model.FinancialSummary
import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.usecase.DeleteTransactionUseCase
import net.sertiva.ourfinances.domain.usecase.GetFinancialSummaryUseCase
import net.sertiva.ourfinances.domain.usecase.GetTransactionsUseCase

class HomeViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getFinancialSummaryUseCase: GetFinancialSummaryUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                combine(
                    getTransactionsUseCase(),
                    getFinancialSummaryUseCase()
                ) { transactions, summary ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        transactions = transactions,
                        financialSummary = summary,
                        error = null
                    )
                }.collect { }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun deleteTransaction(transactionId: Long) {
        viewModelScope.launch {
            deleteTransactionUseCase(transactionId)
                .onSuccess {
                    // Data will be automatically updated via Flow
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun refresh() {
        loadData()
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val financialSummary: FinancialSummary = FinancialSummary(),
    val error: String? = null
)
