// TagDao.kt
package com.example.expensetracker.data.dao

import androidx.room.*
import com.example.expensetracker.data.model.Tag
import com.example.expensetracker.data.model.ExpenseTagCrossRef
import com.example.expensetracker.data.model.relations.ExpenseWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Query("SELECT * FROM tags")
    fun getAllTags(): Flow<List<Tag>>

    @Insert
    suspend fun insertTag(tag: Tag): Long

    @Insert
    suspend fun insertExpenseTagCrossRef(crossRef: ExpenseTagCrossRef)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Transaction
    @Query("SELECT * FROM expenses WHERE expenseId = :expenseId")
    fun getExpenseWithTags(expenseId: Int): Flow<ExpenseWithTags>

    @Query("SELECT * FROM tags WHERE tagId IN (SELECT tagId FROM expense_tag_cross_ref WHERE expenseId = :expenseId)")
    fun getTagsForExpense(expenseId: Int): Flow<List<Tag>>
}