// ui/screens/HomeScreen.kt
package com.example.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.ui.components.*
import com.example.expensetracker.ui.uistate.ExpenseUiEvent
import com.example.expensetracker.ui.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ExpenseViewModel,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Expense Tracker") },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator()
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = { viewModel.onEvent(ExpenseUiEvent.LoadExpenses()) },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Summary card
                        item {
                            SummaryCard(total = uiState.totalExpense)
                        }

                        // Category filters
                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // "All" option
                                item {
                                    FilterChip(
                                        selected = uiState.selectedCategory == null,
                                        onClick = {
                                            viewModel.onEvent(
                                                ExpenseUiEvent.FilterByCategory(null)
                                            )
                                        },
                                        label = { Text("All") }
                                    )
                                }

                                // Category filters
                                items(ExpenseCategory.entries) { category ->
                                    CategoryFilterChip(
                                        category = category,
                                        isSelected = uiState.selectedCategory == category,
                                        onClick = {
                                            viewModel.onEvent(
                                                ExpenseUiEvent.FilterByCategory(category)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        // Expenses list
                        if (uiState.expenses.isEmpty()) {
                            item {
                                EmptyState()
                            }
                        } else {
                            items(
                                items = uiState.expenses,
                                key = { it.id }
                            ) { expense ->
                                ExpenseItem(
                                    expense = expense,
                                    onDelete = { expenseToDelete ->
                                        viewModel.onEvent(
                                            ExpenseUiEvent.DeleteExpense(expenseToDelete)
                                        )
                                    },
                                    onClick = { onExpenseClick(expense.id) }
                                )
                            }
                        }

                        // Bottom spacing
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }
    }
}
