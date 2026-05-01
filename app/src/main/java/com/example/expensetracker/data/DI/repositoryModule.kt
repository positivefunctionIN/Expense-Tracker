package com.example.expensetracker.data.DI

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // @Binds is more efficient than @Provides for simple interface→impl binding.
    // It tells Hilt: "when someone needs ExpenseRepository, give ExpenseRepositoryImpl".
    // The class must be abstract and the function must be abstract too.
    @Binds
    @Singleton
    abstract fun bindExpenseRepository(
        impl: ExpenseRepositoryImpl
    ): ExpenseRepository
}

// For tests — create a test module that overrides this binding:
//
// @TestInstallIn(components = [SingletonComponent::class], replaces = [RepositoryModule::class])
// @Module
// abstract class FakeRepositoryModule {
//     @Binds @Singleton
//     abstract fun bindFakeRepository(fake: FakeExpenseRepository): ExpenseRepository
// }