package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.data.local.Expense
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val viewModel: ExpenseViewModel = hiltViewModel()
                val expenses by viewModel.expenses.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            // Add a dummy expense to test the database
                            viewModel.addExpense(
                                Expense(
                                    id = 0,
                                    amount = 10.0 + (Math.random() * 100),
                                    category = "Food",
                                    description = "Test Expense",
                                    date = Date().toString()
                                )
                            )
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Add Expense")
                        }
                    }
                ) { innerPadding ->
                    ExpenseList(
                        expenses = expenses,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpenseList(expenses: List<Expense>, modifier: Modifier = Modifier) {
    if (expenses.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No expenses yet! Tap + to add one.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(modifier = modifier.padding(16.dp)) {
            item {
                Text(
                    text = "Your Expenses",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            items(expenses) { expense ->
                ExpenseItem(expense)
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = expense.description, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "$${String.format("%.2f", expense.amount)} - ${expense.category}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = expense.date, style = MaterialTheme.typography.bodySmall)
        }
    }
}
