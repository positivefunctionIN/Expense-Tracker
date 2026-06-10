// data/local/expense.kt
package com.example.expensetracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Room Entity - directly mapped to database table
 * Note: Different from domain Expense model
 * This is database-specific
 */
@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: String, // Stored as String, will convert using TypeConverter
    val description: String,
    val date: LocalDateTime, // TypeConverter handles LocalDateTime <-> Long conversion
    val paymentMethod: String
)