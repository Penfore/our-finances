package net.sertiva.ourfinances.domain.usecase

import net.sertiva.ourfinances.data.repository.TransactionRepository
import net.sertiva.ourfinances.domain.model.Transaction

class AddTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transaction: Transaction): Result<Unit> {
        return try {
            repository.insertTransaction(transaction)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
