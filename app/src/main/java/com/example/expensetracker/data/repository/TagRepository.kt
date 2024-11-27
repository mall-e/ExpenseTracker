// TagRepository.kt
package com.example.expensetracker.data.repository

import com.example.expensetracker.data.dao.TagDao
import com.example.expensetracker.data.model.Tag
import com.example.expensetracker.data.model.ExpenseTagCrossRef
import com.example.expensetracker.data.model.relations.ExpenseWithTags
import kotlinx.coroutines.flow.Flow

class TagRepository(private val tagDao: TagDao) {
    fun getAllTags(): Flow<List<Tag>> = tagDao.getAllTags()

    suspend fun insertTag(tag: Tag): Long = tagDao.insertTag(tag)

    suspend fun insertExpenseTagCrossRef(crossRef: ExpenseTagCrossRef) =
        tagDao.insertExpenseTagCrossRef(crossRef)

    suspend fun deleteTag(tag: Tag) = tagDao.deleteTag(tag)

    fun getExpenseWithTags(expenseId: Int): Flow<ExpenseWithTags> =
        tagDao.getExpenseWithTags(expenseId)

    fun getTagsForExpense(expenseId: Int): Flow<List<Tag>> =
        tagDao.getTagsForExpense(expenseId)
}