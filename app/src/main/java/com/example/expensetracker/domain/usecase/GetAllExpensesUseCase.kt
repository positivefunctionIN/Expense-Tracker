// domain/usecase/GetAllExpensesUseCase.kt
package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: Fetch all expenses
 * Single Responsibility: Handle only the logic of getting all expenses
 */
class GetAllExpensesUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return expenseRepository.getAllExpenses()
    }
}

// domain/usecase/AddExpenseUseCase.kt
package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository

/**
 * Use case: Add a new expense
 * Validates and inserts expense into repository
 */
class AddExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Long> = try {
        // Business logic: You can add validation here
        if (expense.amount <= 0) {
            Result.failure(IllegalArgumentException("Amount must be positive"))
        } else if (expense.title.isBlank()) {
            Result.failure(IllegalArgumentException("Title cannot be empty"))
        } else {
            val id = expenseRepository.insertExpense(expense)
            Result.success(id)
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

// domain/usecase/UpdateExpenseUseCase.kt
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

// domain/usecase/DeleteExpenseUseCase.kt
package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.repository.ExpenseRepository

/**
 * Use case: Delete an expense
 */
class DeleteExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: Expense): Result<Unit> = try {
        expenseRepository.deleteExpense(expense)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

// domain/usecase/GetExpensesByCategoryUseCase.kt
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

// domain/usecase/GetTotalExpensesByCategoryUseCase.kt
package com.example.expensetracker.domain.usecase

import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case: Get total spending by category
 * Useful for pie charts and category summaries
 */
class GetTotalExpensesByCategoryUseCase(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(category: ExpenseCategory): Flow<Double> {
        return expenseRepository.getTotalExpensesByCategory(category)
    }
}