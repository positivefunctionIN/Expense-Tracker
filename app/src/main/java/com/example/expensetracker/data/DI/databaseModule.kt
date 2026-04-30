package com.example.expensetracker.data.DI

import android.content.Context
import androidx.room.Room
import com.example.expensetracker.data.local.ExpenseDatabase
import com.example.expensetracker.data.local.ExpenseDao
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
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ExpenseDatabase = Room.databaseBuilder(
        context,
        ExpenseDatabase::class.java,
        "expense_tracker.db"
    ).build()

    @Provides
    fun provideExpenseDao(database: ExpenseDatabase): ExpenseDao =
        database.expenseDao()
}
