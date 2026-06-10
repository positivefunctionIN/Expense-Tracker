// domain/repository/ExpenseRepository.kt
package com.example.expensetracker.domain.repository

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Repository interface in domain layer
 * Defines contracts for what data operations should be available
 * Implementation will be in data layer
 */
interface ExpenseRepository {

    // Read operations
    fun getAllExpenses(): Flow<List<Expense>>

    fun getExpenseById(id: Int): Flow<Expense?>

    fun getExpensesByCategory(category: ExpenseCategory): Flow<List<Expense>>

    fun getExpensesByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Expense>>

    fun getTotalExpensesByCategory(category: ExpenseCategory): Flow<Double>

    // Write operations
    suspend fun insertExpense(expense: Expense): Long

    suspend fun updateExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)

    suspend fun deleteAllExpenses()
}