// UserRepository.kt
package com.example.expensetracker.data.repository

import com.example.expensetracker.data.dao.UserDao
import com.example.expensetracker.data.model.User
import com.example.expensetracker.data.model.relations.UserWithExpenses
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    fun getUserById(userId: Int): Flow<User> = userDao.getUserById(userId)

    fun getUserWithExpenses(userId: Int): Flow<UserWithExpenses> =
        userDao.getUserWithExpenses(userId)

    suspend fun insertUser(user: User): Long = userDao.insertUser(user)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
}