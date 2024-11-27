// ExpenseDao.kt
package com.example.expensetracker.data.dao

import androidx.room.*
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.model.relations.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Transaction
    @Query("SELECT * FROM expenses WHERE expenseId = :expenseId")
    fun getExpenseWithCategory(expenseId: Int): Flow<ExpenseWithCategory>

    @Query("SELECT * FROM expenses WHERE userId = :userId ORDER BY date DESC")
    fun getExpensesByUser(userId: Int): Flow<List<Expense>>

    @Query("SELECT SUM(amount) FROM expenses WHERE userId = :userId")
    fun getTotalExpensesByUser(userId: Int): Flow<Double?>

    @Insert
    suspend fun insertExpense(expense: Expense): Long

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)
}