package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.data.local.Expense

// Entity → Domain (DB layer → app layer)
fun ExpenseEntity.toDomain(): Expense = Expense(
    id = id,
    amount = amount,
    category = category,
    description = description,
    date = date
)

// Domain → Entity (app layer → DB layer)
fun Expense.toEntity(): ExpenseEntity = ExpenseEntity(
    id = id,
    amount = amount,
    category = category,
    description = description,
    date = date
)
