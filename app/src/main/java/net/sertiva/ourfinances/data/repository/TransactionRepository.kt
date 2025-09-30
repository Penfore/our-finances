package net.sertiva.ourfinances.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.sertiva.ourfinances.data.database.dao.TransactionDao
import net.sertiva.ourfinances.data.database.entities.TransactionEntity
import net.sertiva.ourfinances.domain.model.FinancialSummary
import net.sertiva.ourfinances.domain.model.Transaction
import net.sertiva.ourfinances.domain.model.TransactionType
import java.time.LocalDateTime

class TransactionRepository(
    private val transactionDao: TransactionDao
) {

    fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun getTransactionById(id: Long): Transaction? {
        return transactionDao.getTransactionById(id)?.toDomainModel()
    }

    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type.toEntityType()).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getTransactionsByCategory(category: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByCategory(category).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getTransactionsByDateRange(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByDateRange(startDate, endDate).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun getFinancialSummary(): Flow<FinancialSummary> {
        return transactionDao.getAllTransactions().map { entities ->
            val totalIncome = entities.filter { it.type == net.sertiva.ourfinances.data.database.entities.TransactionType.INCOME }
                .sumOf { it.amount }
            val totalExpense = entities.filter { it.type == net.sertiva.ourfinances.data.database.entities.TransactionType.EXPENSE }
                .sumOf { it.amount }

            FinancialSummary(
                totalBalance = totalIncome - totalExpense,
                totalIncome = totalIncome,
                totalExpense = totalExpense,
                transactionCount = entities.size
            )
        }
    }

    fun getAllCategories(): Flow<List<String>> {
        return transactionDao.getAllCategories()
    }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction.toEntity())
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction.toEntity())
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction.toEntity())
    }

    suspend fun deleteTransactionById(id: Long) {
        transactionDao.deleteTransactionById(id)
    }

    suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }
}

private fun TransactionEntity.toDomainModel(): Transaction {
    return Transaction(
        id = id,
        title = title,
        amount = amount,
        category = category,
        type = type.toDomainType(),
        date = date,
        description = description
    )
}

private fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        title = title,
        amount = amount,
        category = category,
        type = type.toEntityType(),
        date = date,
        description = description
    )
}

private fun net.sertiva.ourfinances.data.database.entities.TransactionType.toDomainType(): TransactionType {
    return when (this) {
        net.sertiva.ourfinances.data.database.entities.TransactionType.INCOME -> TransactionType.INCOME
        net.sertiva.ourfinances.data.database.entities.TransactionType.EXPENSE -> TransactionType.EXPENSE
    }
}

private fun TransactionType.toEntityType(): net.sertiva.ourfinances.data.database.entities.TransactionType {
    return when (this) {
        TransactionType.INCOME -> net.sertiva.ourfinances.data.database.entities.TransactionType.INCOME
        TransactionType.EXPENSE -> net.sertiva.ourfinances.data.database.entities.TransactionType.EXPENSE
    }
}
