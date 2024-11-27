// CategoryRepository.kt
package com.example.expensetracker.data.repository

import com.example.expensetracker.data.dao.CategoryDao
import com.example.expensetracker.data.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    fun getAllCategories(): Flow<List<Category>> = categoryDao.getAllCategories()

    fun getCategoryById(categoryId: Int): Flow<Category> =
        categoryDao.getCategoryById(categoryId)

    suspend fun insertCategory(category: Category): Long =
        categoryDao.insertCategory(category)

    suspend fun updateCategory(category: Category) =
        categoryDao.updateCategory(category)

    suspend fun deleteCategory(category: Category) =
        categoryDao.deleteCategory(category)

    suspend fun getCategoryCount(name: String): Int =
        categoryDao.getCategoryCount(name)

    suspend fun deleteAllCategories() = categoryDao.deleteAllCategories()
}