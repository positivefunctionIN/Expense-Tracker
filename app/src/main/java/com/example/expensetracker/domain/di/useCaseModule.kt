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