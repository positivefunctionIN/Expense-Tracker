package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetTotalExpensesByCategoryUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(category: ExpenseCategory): Flow<Double> {
        return expenseRepository.getTotalExpensesByCategory(category)
    }
}