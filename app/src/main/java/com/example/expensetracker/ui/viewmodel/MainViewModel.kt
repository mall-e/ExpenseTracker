// MainViewModel.kt
package com.example.expensetracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.database.ExpenseDatabase
import com.example.expensetracker.data.model.*
import com.example.expensetracker.data.model.relations.UserWithExpenses
import com.example.expensetracker.data.repository.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ExpenseDatabase.getDatabase(application)

    private val userRepository = UserRepository(database.userDao())
    private val categoryRepository = CategoryRepository(database.categoryDao())
    private val expenseRepository = ExpenseRepository(database.expenseDao())
    private val tagRepository = TagRepository(database.tagDao())

    // Current User
    private val _currentUserId = MutableStateFlow<Int?>(null)
    val currentUserId: StateFlow<Int?> = _currentUserId.asStateFlow()

    // UI State
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // User with Expenses
    val currentUserWithExpenses: Flow<UserWithExpenses?> = _currentUserId
        .filterNotNull()
        .flatMapLatest { userId ->
            userRepository.getUserWithExpenses(userId)
        }

    // Categories
    val categories = categoryRepository.getAllCategories()

    // Tags
    val tags = tagRepository.getAllTags()

    // Functions
    fun setCurrentUser(userId: Int) {
        _currentUserId.value = userId
    }

    fun addExpense(
        amount: Double,
        description: String,
        categoryId: Int,
        tagIds: List<Int> = emptyList()
    ) = viewModelScope.launch {
        _currentUserId.value?.let { userId ->
            val expenseId = expenseRepository.insertExpense(
                Expense(
                    userId = userId,
                    categoryId = categoryId,
                    amount = amount,
                    description = description
                )
            ).toInt()

            // Add tags
            tagIds.forEach { tagId ->
                tagRepository.insertExpenseTagCrossRef(
                    ExpenseTagCrossRef(expenseId, tagId)
                )
            }
        }
    }

    fun addCategory(name: String, icon: String, colorHex: String) = viewModelScope.launch {
        categoryRepository.insertCategory(
            Category(
                name = name,
                icon = icon,
                colorHex = colorHex
            )
        )
    }

    fun addTag(name: String, colorHex: String) = viewModelScope.launch {
        tagRepository.insertTag(
            Tag(
                name = name,
                colorHex = colorHex
            )
        )
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        expenseRepository.deleteExpense(expense)
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    fun addUser(name: String, email: String, budgetLimit: Double? = null) = viewModelScope.launch {
        userRepository.insertUser(
            User(
                name = name,
                email = email,
                budgetLimit = budgetLimit
            )
        )
    }

    /*init {
        viewModelScope.launch {
            if (categoryRepository.getCategoryCount("Market") == 0) {
                categoryRepository.insertCategory(Category(name = "Market", icon = "shopping_cart", colorHex = "#2196F3"))
                categoryRepository.insertCategory(Category(name = "Ulaşım", icon = "directions_car", colorHex = "#4CAF50"))
                categoryRepository.insertCategory(Category(name = "Faturalar", icon = "receipt", colorHex = "#FFC107"))
            }
        }
    }*/

    fun resetCategories() = viewModelScope.launch {
        categoryRepository.deleteAllCategories()
        // Temel kategorileri tekrar ekle
        categoryRepository.insertCategory(Category(name = "Market", icon = "shopping_cart", colorHex = "#2196F3"))
        categoryRepository.insertCategory(Category(name = "Ulaşım", icon = "directions_car", colorHex = "#4CAF50"))
        categoryRepository.insertCategory(Category(name = "Faturalar", icon = "receipt", colorHex = "#FFC107"))
    }

}