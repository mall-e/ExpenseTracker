// CategoryDao.kt
package com.example.expensetracker.data.dao

import androidx.room.*
import com.example.expensetracker.data.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE categoryId = :categoryId")
    fun getCategoryById(categoryId: Int): Flow<Category>

    @Insert
    suspend fun insertCategory(category: Category): Long

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT COUNT(*) FROM categories WHERE name = :name")
    suspend fun getCategoryCount(name: String): Int

    @Query("DELETE FROM categories")
    suspend fun deleteAllCategories()
}