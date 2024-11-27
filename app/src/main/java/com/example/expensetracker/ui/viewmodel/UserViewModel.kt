// UserViewModel.kt
package com.example.expensetracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.database.ExpenseDatabase
import com.example.expensetracker.data.model.User
import com.example.expensetracker.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(
        ExpenseDatabase.getDatabase(application).userDao()
    )

    val allUsers: Flow<List<User>> = repository.getAllUsers()

    fun addUser(name: String, email: String, budgetLimit: Double? = null) = viewModelScope.launch {
        repository.insertUser(
            User(
                name = name,
                email = email,
                budgetLimit = budgetLimit
            )
        )
    }

    fun updateUser(user: User) = viewModelScope.launch {
        repository.updateUser(user)
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        repository.deleteUser(user)
    }
}