// ExpenseRepository.kt
package com.example.expensetracker.data.repository

import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.model.relations.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    fun getExpenseWithCategory(expenseId: Int): Flow<ExpenseWithCategory> =
        expenseDao.getExpenseWithCategory(expenseId)

    fun getExpensesByUser(userId: Int): Flow<List<Expense>> =
        expenseDao.getExpensesByUser(userId)

    fun getTotalExpensesByUser(userId: Int): Flow<Double?> =
        expenseDao.getTotalExpensesByUser(userId)

    suspend fun insertExpense(expense: Expense): Long =
        expenseDao.insertExpense(expense)

    suspend fun updateExpense(expense: Expense) =
        expenseDao.updateExpense(expense)

    suspend fun deleteExpense(expense: Expense) =
        expenseDao.deleteExpense(expense)
}