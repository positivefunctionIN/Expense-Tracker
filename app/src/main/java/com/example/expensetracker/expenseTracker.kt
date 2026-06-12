package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.DI.databaseModule
import com.example.expensetracker.domain.di.useCaseModule
import com.example.expensetracker.ui.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class ExpenseTrackerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Start Koin to support your Koin modules
        startKoin {
            androidContext(this@ExpenseTrackerApp)
            modules(
                databaseModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}
