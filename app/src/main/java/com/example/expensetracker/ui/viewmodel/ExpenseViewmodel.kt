package com.example.expensetracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.model.PaymentMethod
import com.example.expensetracker.domain.usecase.*
import com.example.expensetracker.ui.uistate.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * ViewModel for Expense screen
 */
@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getExpensesByCategoryUseCase: GetExpensesByCategoryUseCase,
    private val getTotalExpensesByCategoryUseCase: GetTotalExpensesByCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseUiState())
    val uiState: StateFlow<ExpenseUiState> = _uiState.asStateFlow()

    init {
        loadExpenses()
    }

    fun onEvent(event: ExpenseUiEvent) {
        when (event) {
            is ExpenseUiEvent.LoadExpenses -> loadExpenses()
            is ExpenseUiEvent.FilterByCategory -> filterByCategory(event.category)
            is ExpenseUiEvent.DeleteExpense -> deleteExpense(event.expense)
            is ExpenseUiEvent.ClearError -> clearError()
        }
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getAllExpensesUseCase()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Unknown error occurred"
                        )
                    }
                }
                .collect { expenses ->
                    val total = expenses.sumOf { it.amount }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            expenses = expenses,
                            totalExpense = total,
                            error = null
                        )
                    }
                }
        }
    }

    private fun filterByCategory(category: ExpenseCategory?) {
        viewModelScope.launch {
            if (category == null) {
                loadExpenses()
            } else {
                _uiState.update { it.copy(isLoading = true, selectedCategory = category) }

                getExpensesByCategoryUseCase(category)
                    .catch { e ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = e.message ?: "Unknown error occurred"
                            )
                        }
                    }
                    .collect { expenses ->
                        val total = expenses.sumOf { it.amount }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                expenses = expenses,
                                totalExpense = total,
                                error = null
                            )
                        }
                    }
            }
        }
    }

    private fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            deleteExpenseUseCase(expense).onFailure { e ->
                _uiState.update {
                    it.copy(error = e.message ?: "Failed to delete expense")
                }
            }
            loadExpenses()
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

/**
 * ViewModel for Add/Edit Expense screen
 */
@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditExpenseUiState())
    val uiState: StateFlow<AddEditExpenseUiState> = _uiState.asStateFlow()

    fun onEvent(event: AddEditExpenseUiEvent) {
        when (event) {
            is AddEditExpenseUiEvent.TitleChanged -> {
                _uiState.update { it.copy(title = event.title) }
            }
            is AddEditExpenseUiEvent.AmountChanged -> {
                _uiState.update { it.copy(amount = event.amount) }
            }
            is AddEditExpenseUiEvent.CategoryChanged -> {
                _uiState.update { it.copy(category = event.category) }
            }
            is AddEditExpenseUiEvent.DescriptionChanged -> {
                _uiState.update { it.copy(description = event.description) }
            }
            is AddEditExpenseUiEvent.PaymentMethodChanged -> {
                _uiState.update { it.copy(paymentMethod = event.method) }
            }
            is AddEditExpenseUiEvent.SaveExpense -> saveExpense(event.date)
            is AddEditExpenseUiEvent.ClearError -> clearError()
        }
    }

    private fun saveExpense(date: LocalDateTime) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val amount = _uiState.value.amount.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid amount")

                val expense = Expense(
                    id = _uiState.value.editingExpenseId ?: 0,
                    title = _uiState.value.title,
                    amount = amount,
                    category = _uiState.value.category,
                    description = _uiState.value.description,
                    date = date,
                    paymentMethod = PaymentMethod.valueOf(_uiState.value.paymentMethod)
                )

                val result = if (expense.id > 0) {
                    updateExpenseUseCase(expense)
                } else {
                    addExpenseUseCase(expense).mapCatching { Unit }
                }

                result.onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, isSuccess = true, error = null)
                    }
                }.onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to save expense"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message ?: "Error occurred")
                }
            }
        }
    }

    private fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun setEditingExpense(expense: Expense) {
        _uiState.update {
            it.copy(
                title = expense.title,
                amount = expense.amount.toString(),
                category = expense.category,
                description = expense.description,
                paymentMethod = expense.paymentMethod.name,
                editingExpenseId = expense.id
            )
        }
    }
}
