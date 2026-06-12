package com.example.expensetracker.data.DI

import com.example.expensetracker.data.local.ExpenseTrackerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module for database dependencies
 */
val databaseModule = module {
    single { ExpenseTrackerDatabase.getInstance(androidContext()) }
    single { get<ExpenseTrackerDatabase>().expenseDAO() }
}
