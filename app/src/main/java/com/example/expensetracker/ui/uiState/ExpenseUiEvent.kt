// ui/uistate/ExpenseUiState.kt
package com.example.expensetracker.ui.uistate

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory

/**
 * UI State for expense screen
 * Represents all possible states of the expense list screen
 * Single source of truth for UI
 */
data class ExpenseUiState(
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: ExpenseCategory? = null,
    val totalExpense: Double = 0.0
)

/**
 * UI State for add/edit expense screen
 */
data class AddEditExpenseUiState(
    val title: String = "",
    val amount: String = "",
    val category: ExpenseCategory = ExpenseCategory.OTHER,
    val description: String = "",
    val paymentMethod: String = "CASH",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val editingExpenseId: Int? = null
)

// ui/uistate/ExpenseUiEvent.kt
package com.example.expensetracker.ui.uistate

import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import java.time.LocalDateTime

/**
 * UI Events - user actions triggered from UI
 * Sent from Composables to ViewModel
 */
sealed class ExpenseUiEvent {
    data class LoadExpenses(val category: ExpenseCategory? = null) : ExpenseUiEvent()
    data class FilterByCategory(val category: ExpenseCategory?) : ExpenseUiEvent()
    data class DeleteExpense(val expense: Expense) : ExpenseUiEvent()
    object ClearError : ExpenseUiEvent()
}

/**
 * Add/Edit Expense UI Events
 */
sealed class AddEditExpenseUiEvent {
    data class TitleChanged(val title: String) : AddEditExpenseUiEvent()
    data class AmountChanged(val amount: String) : AddEditExpenseUiEvent()
    data class CategoryChanged(val category: ExpenseCategory) : AddEditExpenseUiEvent()
    data class DescriptionChanged(val description: String) : AddEditExpenseUiEvent()
    data class PaymentMethodChanged(val method: String) : AddEditExpenseUiEvent()
    data class SaveExpense(val date: LocalDateTime) : AddEditExpenseUiEvent()
    object ClearError : AddEditExpenseUiEvent()
}