package net.sertiva.ourfinances.utils

import android.content.Context
import net.sertiva.ourfinances.data.database.FinanceDatabase
import net.sertiva.ourfinances.data.repository.TransactionRepository
import net.sertiva.ourfinances.domain.usecase.AddTransactionUseCase
import net.sertiva.ourfinances.domain.usecase.DeleteTransactionUseCase
import net.sertiva.ourfinances.domain.usecase.GetFinancialSummaryUseCase
import net.sertiva.ourfinances.domain.usecase.GetTransactionsUseCase
import net.sertiva.ourfinances.ui.viewmodel.AddTransactionViewModel
import net.sertiva.ourfinances.ui.viewmodel.HomeViewModel

object DependencyFactory {

    fun createHomeViewModel(context: Context): HomeViewModel {
        val database = FinanceDatabase.getDatabase(context)
        val repository = TransactionRepository(database.transactionDao())

        val getTransactionsUseCase = GetTransactionsUseCase(repository)
        val getFinancialSummaryUseCase = GetFinancialSummaryUseCase(repository)
        val deleteTransactionUseCase = DeleteTransactionUseCase(repository)

        return HomeViewModel(
            getTransactionsUseCase = getTransactionsUseCase,
            getFinancialSummaryUseCase = getFinancialSummaryUseCase,
            deleteTransactionUseCase = deleteTransactionUseCase
        )
    }

    fun createAddTransactionViewModel(context: Context): AddTransactionViewModel {
        val repository = createRepository(context)
        val addTransactionUseCase = AddTransactionUseCase(repository)
        return AddTransactionViewModel(addTransactionUseCase)
    }

    fun createRepository(context: Context): TransactionRepository {
        val database = FinanceDatabase.getDatabase(context)
        return TransactionRepository(database.transactionDao())
    }

    fun createAddTransactionUseCase(context: Context): AddTransactionUseCase {
        val repository = createRepository(context)
        return AddTransactionUseCase(repository)
    }
}
