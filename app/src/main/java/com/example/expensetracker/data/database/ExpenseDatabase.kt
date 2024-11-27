// ExpenseDatabase.kt
package com.example.expensetracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.data.dao.CategoryDao
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.dao.TagDao
import com.example.expensetracker.data.dao.UserDao
import com.example.expensetracker.data.model.Category
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.model.ExpenseTagCrossRef
import com.example.expensetracker.data.model.Tag
import com.example.expensetracker.data.model.User

@Database(
    entities = [
        User::class,
        Category::class,
        Tag::class,
        ExpenseTagCrossRef::class,
        Expense::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun tagDao(): TagDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDatabase::class.java,
                    "expense_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}