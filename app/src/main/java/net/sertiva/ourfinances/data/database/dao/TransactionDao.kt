package net.sertiva.ourfinances.data.database.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.sertiva.ourfinances.data.database.entities.TransactionEntity
import net.sertiva.ourfinances.data.database.entities.TransactionType
import java.time.LocalDateTime

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: Long): TransactionEntity?

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getTransactionsByType(type: TransactionType): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE category = :category ORDER BY date DESC")
    fun getTransactionsByCategory(category: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getTransactionsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<TransactionEntity>>

    @Query("SELECT SUM(CASE WHEN type = 'INCOME' THEN amount ELSE -amount END) FROM transactions")
    fun getTotalBalance(): Flow<Double?>

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type")
    fun getTotalByType(type: TransactionType): Flow<Double?>

    @Query("SELECT DISTINCT category FROM transactions ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Long)

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}
