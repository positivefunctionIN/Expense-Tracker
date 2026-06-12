package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Long> = try {
        if (expense.amount <= 0) {
            Result.failure(IllegalArgumentException("Amount must be positive"))
        } else {
            Result.success(expenseRepository.insertExpense(expense))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
