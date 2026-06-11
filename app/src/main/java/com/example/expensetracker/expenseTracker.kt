// expenseTracker.kt (Application class)
package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.di.databaseModule
import com.example.expensetracker.data.di.repositoryModule
import com.example.expensetracker.domain.di.useCaseModule
import com.example.expensetracker.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Application class
 * Initializes Koin dependency injection
 */
class ExpenseTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@ExpenseTrackerApp)
            modules(
                databaseModule,
                repositoryModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}

