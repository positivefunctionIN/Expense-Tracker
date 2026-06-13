package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: Get expenses filtered by category
 */
class GetExpensesByCategoryUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(category: ExpenseCategory): Flow<List<Expense>> {
        return expenseRepository.getExpensesByCategory(category)
    }
}