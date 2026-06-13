// domain/usecase/GetAllExpensesUseCase.kt
package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetAllExpensesUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return expenseRepository.getAllExpenses()
    }
}