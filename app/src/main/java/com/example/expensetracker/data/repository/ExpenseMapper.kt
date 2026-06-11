// data/repository/ExpenseMapper.kt
package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.ExpenseEntity
import com.example.expensetracker.domain.model.Expense
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.model.PaymentMethod

/**
 * Mapper to convert between data layer and domain layer
 * Single Responsibility: Convert ExpenseEntity ↔ Expense
 *
 * Why separate? Domain layer shouldn't know about database/Room
 */
class ExpenseMapper {

    fun toDomain(entity: ExpenseEntity): Expense {
        return Expense(
            id = entity.id,
            title = entity.title,
            amount = entity.amount,
            category = ExpenseCategory.valueOf(entity.category),
            description = entity.description,
            date = entity.date,
            paymentMethod = PaymentMethod.valueOf(entity.paymentMethod)
        )
    }

    fun toEntity(domain: Expense): ExpenseEntity {
        return ExpenseEntity(
            id = domain.id,
            title = domain.title,
            amount = domain.amount,
            category = domain.category.name,
            description = domain.description,
            date = domain.date,
            paymentMethod = domain.paymentMethod.name
        )
    }

    fun toDomainList(entities: List<ExpenseEntity>): List<Expense> {
        return entities.map { toDomain(it) }
    }

    fun toEntityList(domains: List<Expense>): List<ExpenseEntity> {
        return domains.map { toEntity(it) }
    }
}