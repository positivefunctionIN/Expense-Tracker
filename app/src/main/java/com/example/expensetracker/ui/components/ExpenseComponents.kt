// ui/components/ExpenseComponents.kt
package com.example.expensetracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import java.time.format.DateTimeFormatter

/**
 * Expense Item Card
 * Displays a single expense in a nice card format
 */
@Composable
fun ExpenseItem(
    expense: Expense,
    onDelete: (Expense) -> Unit,
    onClick: (Expense) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(expense) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category indicator
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = getCategoryColor(expense.category),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = expense.category.displayName.first().toString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            // Title and date
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = expense.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    text = expense.date.format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Amount and delete button
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "₹${String.format("%.2f", expense.amount)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.error
                )

                IconButton(
                    onClick = { onDelete(expense) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Category Filter Chip
 * Clickable chip to filter expenses by category
 */
@Composable
fun CategoryFilterChip(
    category: ExpenseCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(category.displayName) },
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = getCategoryColor(category).copy(alpha = 0.1f),
            selectedContainerColor = getCategoryColor(category)
        )
    )
}

/**
 * Summary Card
 * Shows total expenses
 */
@Composable
fun SummaryCard(
    total: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Expenses",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp
            )
            Text(
                text = "₹${String.format("%.2f", total)}",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * Get color based on category
 */
@Composable
fun getCategoryColor(category: ExpenseCategory): androidx.compose.ui.graphics.Color {
    return when (category) {
        ExpenseCategory.FOOD -> androidx.compose.ui.graphics.Color(0xFFFF6B6B)
        ExpenseCategory.TRANSPORTATION -> androidx.compose.ui.graphics.Color(0xFF4ECDC4)
        ExpenseCategory.ENTERTAINMENT -> androidx.compose.ui.graphics.Color(0xFFFFE66D)
        ExpenseCategory.SHOPPING -> androidx.compose.ui.graphics.Color(0xFF95E1D3)
        ExpenseCategory.UTILITIES -> androidx.compose.ui.graphics.Color(0xFFC7CEEA)
        ExpenseCategory.HEALTHCARE -> androidx.compose.ui.graphics.Color(0xFFFF6B9D)
        ExpenseCategory.EDUCATION -> androidx.compose.ui.graphics.Color(0xFF74B9FF)
        ExpenseCategory.TRAVEL -> androidx.compose.ui.graphics.Color(0xFFA29BFE)
        ExpenseCategory.OTHER -> androidx.compose.ui.graphics.Color(0xFFDFE6E9)
    }
}

/**
 * Loading indicator
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Error message display
 */
@Composable
fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Snackbar(
        modifier = modifier,
        action = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    ) {
        Text(message)
    }
}

/**
 * Empty state
 */
@Composable
fun EmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No expenses yet",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Add your first expense to get started",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}