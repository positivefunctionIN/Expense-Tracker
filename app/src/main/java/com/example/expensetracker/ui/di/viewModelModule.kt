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