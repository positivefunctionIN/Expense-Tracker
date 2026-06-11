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