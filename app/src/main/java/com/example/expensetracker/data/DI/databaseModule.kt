package com.example.expensetracker.data.DI

import android.content.Context
import androidx.room.Room
import com.yourapp.data.local.AppDatabase
import com.yourapp.data.local.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton  // one database instance for the entire app lifetime
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "expense_tracker.db"  // SQLite filename on disk
    )
        // .fallbackToDestructiveMigration()  ← add during dev only, REMOVE before release
        // Without it, changing schema without a Migration crashes on upgrade.
        // With it, the database is wiped on schema change — fine for dev, never for prod.
        .build()

    @Provides
    // No @Singleton here — DAO is lightweight, AppDatabase already is singleton
    fun provideExpenseDao(database: AppDatabase): ExpenseDao =
        database.expenseDao()
}