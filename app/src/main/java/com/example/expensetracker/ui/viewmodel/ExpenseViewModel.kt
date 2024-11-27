// ExpenseViewModel.kt
package com.example.expensetracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.database.ExpenseDatabase
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ExpenseRepository(
        ExpenseDatabase.getDatabase(application).expenseDao()
    )

    fun getExpensesByUser(userId: Int): Flow<List<Expense>> =
        repository.getExpensesByUser(userId)

    fun getTotalExpensesByUser(userId: Int): Flow<Double?> =
        repository.getTotalExpensesByUser(userId)

    fun addExpense(
        userId: Int,
        categoryId: Int,
        amount: Double,
        description: String
    ) = viewModelScope.launch {
        repository.insertExpense(
            Expense(
                userId = userId,
                categoryId = categoryId,
                amount = amount,
                description = description
            )
        )
    }

    fun updateExpense(expense: Expense) = viewModelScope.launch {
        repository.updateExpense(expense)
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        repository.deleteExpense(expense)
    }
}