// domain/model/Expense.kt
package com.example.expensetracker.domain.model

import java.time.LocalDateTime

/**
 * Domain model for Expense
 * This is independent of any framework/database concerns
 * Pure Kotlin data class representing business logic
 */
data class Expense(
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val description: String = "",
    val date: LocalDateTime,
    val paymentMethod: PaymentMethod = PaymentMethod.CASH
)

enum class ExpenseCategory(val displayName: String) {
    FOOD("Food & Dining"),
    TRANSPORTATION("Transportation"),
    ENTERTAINMENT("Entertainment"),
    SHOPPING("Shopping"),
    UTILITIES("Utilities"),
    HEALTHCARE("Healthcare"),
    EDUCATION("Education"),
    TRAVEL("Travel"),
    OTHER("Other")
}

enum class PaymentMethod(val displayName: String) {
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    ONLINE("Online"),
    WALLET("Digital Wallet")
}
