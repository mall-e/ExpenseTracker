// UserDao.kt
package com.example.expensetracker.data.dao

import androidx.room.*
import com.example.expensetracker.data.model.User
import com.example.expensetracker.data.model.relations.UserWithExpenses
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: Int): Flow<User>

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserWithExpenses(userId: Int): Flow<UserWithExpenses>

    @Insert
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}