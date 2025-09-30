package net.sertiva.ourfinances.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import net.sertiva.ourfinances.data.database.dao.TransactionDao
import net.sertiva.ourfinances.data.database.entities.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FinanceDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: FinanceDatabase? = null

        fun getDatabase(context: Context): FinanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinanceDatabase::class.java,
                    "finance_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
