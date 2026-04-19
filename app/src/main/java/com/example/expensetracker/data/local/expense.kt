package com.example.expensetracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val category: String,
    val description: String,
    val date: String
)

// Domain model
data class Expense(
    val id: Long,
    val amount: Double,
    val category: String,
    val description: String,
    val date: String
)

//Wrapper functions to convert between the two

//From Database to App logic:
fun ExpenseEntity.toDomain() = Expense(
    id = id,
    amount = amount,
    category = category,
    description = description,
    date = date
)
//From App logic to Database:
fun Expense.toEntity() = ExpenseEntity(
    id = id,
    amount = amount,
    category = category,
    description = description,
    date = date
)
