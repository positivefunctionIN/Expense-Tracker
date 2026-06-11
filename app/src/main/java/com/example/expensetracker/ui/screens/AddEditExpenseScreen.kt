// ui/screens/AddEditExpenseScreen.kt
package com.example.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.model.PaymentMethod
import com.example.expensetracker.ui.components.ErrorMessage
import com.example.expensetracker.ui.uistate.AddEditExpenseUiEvent
import com.example.expensetracker.ui.viewmodel.AddEditExpenseViewModel
import java.time.LocalDateTime

/**
 * Add/Edit Expense Screen
 * Form for creating or editing expenses
 */
@Composable
fun AddEditExpenseScreen(
    viewModel: AddEditExpenseViewModel,
    onNavigateBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showPaymentMethodDropdown by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.value.isSuccess) {
        if (uiState.value.isSuccess) {
            onSaveSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.value.editingExpenseId != null)
                            "Edit Expense"
                        else
                            "Add Expense"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Title field
            TextField(
                value = uiState.value.title,
                onValueChange = { newValue ->
                    viewModel.onEvent(AddEditExpenseUiEvent.TitleChanged(newValue))
                },
                label = { Text("Title") },
                placeholder = { Text("e.g., Grocery Shopping") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Amount field
            TextField(
                value = uiState.value.amount,
                onValueChange = { newValue ->
                    viewModel.onEvent(AddEditExpenseUiEvent.AmountChanged(newValue))
                },
                label = { Text("Amount (₹)") },
                placeholder = { Text("0.00") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Category dropdown
            ExposedDropdownMenuBox(
                expanded = showCategoryDropdown,
                onExpandedChange = { showCategoryDropdown = !showCategoryDropdown }
            ) {
                TextField(
                    value = uiState.value.category.displayName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategoryDropdown) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = showCategoryDropdown,
                    onDismissRequest = { showCategoryDropdown = false }
                ) {
                    ExpenseCategory.values().forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.displayName) },
                            onClick = {
                                viewModel.onEvent(AddEditExpenseUiEvent.CategoryChanged(category))
                                showCategoryDropdown = false
                            }
                        )
                    }
                }
            }

            // Payment method dropdown
            ExposedDropdownMenuBox(
                expanded = showPaymentMethodDropdown,
                onExpandedChange = { showPaymentMethodDropdown = !showPaymentMethodDropdown }
            ) {
                TextField(
                    value = uiState.value.paymentMethod,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Payment Method") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showPaymentMethodDropdown) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = showPaymentMethodDropdown,
                    onDismissRequest = { showPaymentMethodDropdown = false }
                ) {
                    PaymentMethod.values().forEach { method ->
                        DropdownMenuItem(
                            text = { Text(method.displayName) },
                            onClick = {
                                viewModel.onEvent(AddEditExpenseUiEvent.PaymentMethodChanged(method.name))
                                showPaymentMethodDropdown = false
                            }
                        )
                    }
                }
            }

            // Description field
            TextField(
                value = uiState.value.description,
                onValueChange = { newValue ->
                    viewModel.onEvent(AddEditExpenseUiEvent.DescriptionChanged(newValue))
                },
                label = { Text("Description (Optional)") },
                placeholder = { Text("Add notes...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )

            // Date button
            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Select Date: ${selectedDate.toLocalDate()}")
            }

            // Error message
            if (uiState.value.error != null) {
                ErrorMessage(
                    message = uiState.value.error!!,
                    onDismiss = {
                        viewModel.onEvent(AddEditExpenseUiEvent.ClearError)
                    }
                )
            }

            // Save button
            Button(
                onClick = {
                    viewModel.onEvent(AddEditExpenseUiEvent.SaveExpense(selectedDate))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !uiState.value.isLoading
            ) {
                if (uiState.value.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Save Expense")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}