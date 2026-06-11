// MainActivity.kt
package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetracker.ui.screens.AddEditExpenseScreen
import com.example.expensetracker.ui.screens.HomeScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.ui.viewmodel.AddEditExpenseViewModel
import com.example.expensetracker.ui.viewmodel.ExpenseViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Main Activity
 * Sets up navigation and theme
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseTrackerNavigation()
                }
            }
        }
    }
}

/**
 * Navigation routes
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddExpense : Screen("add_expense")
    object EditExpense : Screen("edit_expense/{expenseId}") {
        fun createRoute(expenseId: Int) = "edit_expense/$expenseId"
    }
}

/**
 * Navigation setup
 */
@Composable
fun ExpenseTrackerNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val viewModel: ExpenseViewModel = koinViewModel()
            HomeScreen(
                viewModel = viewModel,
                onAddExpenseClick = {
                    navController.navigate(Screen.AddExpense.route)
                },
                onExpenseClick = { expenseId ->
                    navController.navigate(Screen.EditExpense.createRoute(expenseId))
                }
            )
        }

        composable(Screen.AddExpense.route) {
            val viewModel: AddEditExpenseViewModel = koinViewModel()
            AddEditExpenseScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack(Screen.Home.route, false)
                }
            )
        }

        composable(
            route = Screen.EditExpense.route,
            arguments = listOf(
                navArgument("expenseId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getInt("expenseId") ?: 0
            val viewModel: AddEditExpenseViewModel = koinViewModel()
            // TODO: Load expense by ID and set in viewModel
            // This would require another use case to get expense by ID
            AddEditExpenseScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack(Screen.Home.route, false)
                }
            )
        }
    }
}