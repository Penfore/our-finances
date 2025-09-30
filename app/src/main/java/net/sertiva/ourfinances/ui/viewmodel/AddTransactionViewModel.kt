package net.sertiva.ourfinances.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.model.TransactionType
import net.sertiva.ourfinances.domain.usecase.AddTransactionUseCase
import java.time.LocalDateTime

class AddTransactionViewModel(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTransactionUiState())
    val uiState: StateFlow<AddTransactionUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            title = title,
            titleError = null
        )
    }

    fun updateAmount(amount: String) {
        _uiState.value = _uiState.value.copy(
            amount = amount,
            amountError = null
        )
    }

    fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(
            category = category,
            categoryError = null
        )
    }

    fun updateType(type: TransactionType) {
        _uiState.value = _uiState.value.copy(type = type)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun updateDate(date: LocalDateTime) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun saveTransaction() {
        val currentState = _uiState.value

        // Validate inputs
        val titleError = if (currentState.title.isBlank()) "Title is required" else null
        val amountError = if (currentState.amount.isBlank()) "Amount is required"
                         else if (currentState.amount.toDoubleOrNull() == null) "Invalid amount"
                         else if (currentState.amount.toDoubleOrNull()!! <= 0) "Amount must be greater than 0"
                         else null
        val categoryError = if (currentState.category.isBlank()) "Category is required" else null

        if (titleError != null || amountError != null || categoryError != null) {
            _uiState.value = currentState.copy(
                titleError = titleError,
                amountError = amountError,
                categoryError = categoryError
            )
            return
        }

        val transaction = Transaction(
            title = currentState.title.trim(),
            amount = currentState.amount.toDouble(),
            category = currentState.category.trim(),
            type = currentState.type,
            date = currentState.date,
            description = if (currentState.description.isBlank()) null else currentState.description.trim()
        )

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true)

            addTransactionUseCase(transaction)
                .onSuccess {
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        isSaved = true
                    )
                }
                .onFailure { error ->
                    _uiState.value = currentState.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to save transaction"
                    )
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun resetState() {
        _uiState.value = AddTransactionUiState()
    }
}

data class AddTransactionUiState(
    val title: String = "",
    val amount: String = "",
    val category: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val date: LocalDateTime = LocalDateTime.now(),
    val description: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null,
    val titleError: String? = null,
    val amountError: String? = null,
    val categoryError: String? = null
)
