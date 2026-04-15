package com.example.expensetracker.ui.database


data class Expense (
    val id: String,
    val amount: Double,
    val description: String,
    val category: String,
    val date: Long,
    val note: String = ""
)

enum class Category { FOOD, TRANSPORT, HEALTH, OTHER }