package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.Expense
import kotlinx.coroutines.flow.Flow

// Repository interface
interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun insertExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
}
