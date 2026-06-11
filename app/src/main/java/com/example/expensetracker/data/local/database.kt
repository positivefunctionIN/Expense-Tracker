// data/local/database.kt
package com.example.expensetracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Room Database
 * Manages the SQLite database and provides DAOs
 */
@Database(
    entities = [ExpenseEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExpenseTrackerDatabase : RoomDatabase() {

    abstract fun expenseDAO(): ExpenseDAO

    companion object {
        private const val DATABASE_NAME = "expense_tracker.db"

        // Singleton pattern to ensure only one database instance
        @Volatile
        private var INSTANCE: ExpenseTrackerDatabase? = null

        fun getInstance(context: Context): ExpenseTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseTrackerDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration() // For development only
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}