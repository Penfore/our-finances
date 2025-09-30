package net.sertiva.ourfinances.domain.usecase

import kotlinx.coroutines.flow.Flow
import net.sertiva.ourfinances.data.repository.TransactionRepository
import net.sertiva.ourfinances.domain.model.Transaction

class GetTransactionsUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<Transaction>> {
        return repository.getAllTransactions()
    }
}
