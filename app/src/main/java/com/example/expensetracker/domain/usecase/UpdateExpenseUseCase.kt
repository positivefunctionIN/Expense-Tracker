package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository

/**
 * Use case: Update an existing expense
 */
class UpdateExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Unit> = try {
        if (expense.amount <= 0) {
            Result.failure(IllegalArgumentException("Amount must be positive"))
        } else if (expense.title.isBlank()) {
            Result.failure(IllegalArgumentException("Title cannot be empty"))
        } else {
            expenseRepository.updateExpense(expense)
            Result.success(Unit)
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}