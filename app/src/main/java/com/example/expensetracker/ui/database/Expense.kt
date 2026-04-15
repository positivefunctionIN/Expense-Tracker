package com.example.expensetracker.ui.database

import android.provider.ContactsContract

data class Expense (
    val id: String,
    val amount: Double,
    val description: String,
    val category: String,
    val date: Long,
    val note: String = ""
)