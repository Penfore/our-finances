package net.sertiva.ourfinances.domain.usecase

import net.sertiva.ourfinances.data.repository.TransactionRepository

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Long): Result<Unit> {
        return try {
            repository.deleteTransactionById(transactionId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
