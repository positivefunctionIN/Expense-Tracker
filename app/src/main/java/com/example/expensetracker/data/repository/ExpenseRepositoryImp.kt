// data/repository/ExpenseRepository.kt (Implementation)
package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.ExpenseDAO
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Repository Implementation
 * Concrete implementation of domain repository interface
 * Maps data layer to domain layer using mapper
 */
class ExpenseRepositoryImpl(
    private val expenseDAO: ExpenseDAO,
    private val mapper: ExpenseMapper
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDAO.getAllExpenses()
            .map { entities -> mapper.toDomainList(entities) }
    }

    override fun getExpenseById(id: Int): Flow<Expense?> {
        return expenseDAO.getExpenseById(id)
            .map { entity -> entity?.let { mapper.toDomain(it) } }
    }

    override fun getExpensesByCategory(category: ExpenseCategory): Flow<List<Expense>> {
        return expenseDAO.getExpensesByCategory(category.name)
            .map { entities -> mapper.toDomainList(entities) }
    }

    override fun getExpensesByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<Expense>> {
        val startDateTime = startDate.atStartOfDay()
        val endDateTime = endDate.atTime(LocalTime.MAX)

        return expenseDAO.getExpensesByDateRange(startDateTime, endDateTime)
            .map { entities -> mapper.toDomainList(entities) }
    }

    override fun getTotalExpensesByCategory(category: ExpenseCategory): Flow<Double> {
        return expenseDAO.getTotalExpensesByCategory(category.name)
    }

    override suspend fun insertExpense(expense: Expense): Long {
        val entity = mapper.toEntity(expense)
        return expenseDAO.insertExpense(entity)
    }

    override suspend fun updateExpense(expense: Expense) {
        val entity = mapper.toEntity(expense)
        expenseDAO.updateExpense(entity)
    }

    override suspend fun deleteExpense(expense: Expense) {
        val entity = mapper.toEntity(expense)
        expenseDAO.deleteExpense(entity)
    }

    override suspend fun deleteAllExpenses() {
        expenseDAO.deleteAllExpenses()
    }
}