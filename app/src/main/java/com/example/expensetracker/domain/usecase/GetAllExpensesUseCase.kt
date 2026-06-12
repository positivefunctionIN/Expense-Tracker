package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> = expenseRepository.getAllExpenses()
}

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

class UpdateExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Unit> = try {
        expenseRepository.updateExpense(expense)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

class DeleteExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Unit> = try {
        expenseRepository.deleteExpense(expense)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

class GetExpensesByCategoryUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(category: ExpenseCategory): Flow<List<Expense>> =
        expenseRepository.getExpensesByCategory(category)
}

class GetTotalExpensesByCategoryUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(category: ExpenseCategory): Flow<Double> =
        expenseRepository.getTotalExpensesByCategory(category)
}
