package net.sertiva.ourfinances.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.sertiva.ourfinances.data.repository.TransactionRepository
import net.sertiva.ourfinances.domain.model.FinancialSummary
import net.sertiva.ourfinances.domain.model.TransactionType

class GetFinancialSummaryUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<FinancialSummary> {
        return repository.getAllTransactions().map { transactions ->
            val income = transactions
                .filter { it.type == TransactionType.INCOME }
                .sumOf { it.amount }

            val expense = transactions
                .filter { it.type == TransactionType.EXPENSE }
                .sumOf { it.amount }

            FinancialSummary(
                totalIncome = income,
                totalExpense = expense,
                totalBalance = income - expense,
                transactionCount = transactions.size
            )
        }
    }
}
