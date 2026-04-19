package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    suspend fun insertExpense(expense: ExpenseEntity)
    suspend fun deleteExpense(expense: ExpenseEntity)
}
