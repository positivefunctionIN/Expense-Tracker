// data/di/databaseModule.kt
package com.example.expensetracker.data.di

import android.content.Context
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

// data/di/repositoryModule.kt
package com.example.expensetracker.data.di

import com.example.expensetracker.data.repository.ExpenseMapper
import com.example.expensetracker.data.repository.ExpenseRepositoryImpl
import com.example.expensetracker.domain.repository.ExpenseRepository
import org.koin.dsl.module

/**
 * Koin module for repository and mapper dependencies
 */
val repositoryModule = module {
    single { ExpenseMapper() }
    single<ExpenseRepository> {
        ExpenseRepositoryImpl(
            expenseDAO = get(),
            mapper = get()
        )
    }
}

// domain/di/useCaseModule.kt (NEW)
package com.example.expensetracker.domain.di

import com.example.expensetracker.domain.usecase.*
import org.koin.dsl.module

/**
 * Koin module for use case dependencies
 */
val useCaseModule = module {
    single { GetAllExpensesUseCase(get()) }
    single { AddExpenseUseCase(get()) }
    single { UpdateExpenseUseCase(get()) }
    single { DeleteExpenseUseCase(get()) }
    single { GetExpensesByCategoryUseCase(get()) }
    single { GetTotalExpensesByCategoryUseCase(get()) }
}

// ui/di/viewModelModule.kt (NEW)
package com.example.expensetracker.ui.di

import com.example.expensetracker.ui.viewmodel.AddEditExpenseViewModel
import com.example.expensetracker.ui.viewmodel.ExpenseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for ViewModel dependencies
 */
val viewModelModule = module {
    viewModel {
        ExpenseViewModel(
            getAllExpensesUseCase = get(),
            addExpenseUseCase = get(),
            updateExpenseUseCase = get(),
            deleteExpenseUseCase = get(),
            getExpensesByCategoryUseCase = get(),
            getTotalExpensesByCategoryUseCase = get()
        )
    }

    viewModel {
        AddEditExpenseViewModel(
            addExpenseUseCase = get(),
            updateExpenseUseCase = get()
        )
    }
}