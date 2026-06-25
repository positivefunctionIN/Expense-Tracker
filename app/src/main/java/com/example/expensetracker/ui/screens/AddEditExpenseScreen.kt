package com.example.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    viewModel: AddEditExpenseViewModel,
    onNavigateBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showPaymentMethodDropdown by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onSaveSuccess()
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        // Minimal conversion for example
                        // selectedDate = ... 
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (uiState.editingExpenseId != null) "Edit Expense" else "Add Expense"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                value = uiState.title,
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
                value = uiState.amount,
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
                onExpandedChange = { showCategoryDropdown = it }
            ) {
                TextField(
                    value = uiState.category.displayName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategoryDropdown) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = showCategoryDropdown,
                    onDismissRequest = { showCategoryDropdown = false }
                ) {
                    ExpenseCategory.entries.forEach { category ->
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
                onExpandedChange = { showPaymentMethodDropdown = it }
            ) {
                TextField(
                    value = uiState.paymentMethod,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Payment Method") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showPaymentMethodDropdown) },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = showPaymentMethodDropdown,
                    onDismissRequest = { showPaymentMethodDropdown = false }
                ) {
                    PaymentMethod.entries.forEach { method ->
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
                value = uiState.description,
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
                Text("Date: ${selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
            }

            // Error message
            if (uiState.error != null) {
                ErrorMessage(
                    message = uiState.error!!,
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
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save Expense")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
