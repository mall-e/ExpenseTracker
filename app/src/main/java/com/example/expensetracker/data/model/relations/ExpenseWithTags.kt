// ExpenseWithTags.kt
package com.example.expensetracker.data.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.model.ExpenseTagCrossRef
import com.example.expensetracker.data.model.Tag

data class ExpenseWithTags(
    @Embedded val expense: Expense,
    @Relation(
        parentColumn = "expenseId",
        entityColumn = "tagId",
        associateBy = Junction(ExpenseTagCrossRef::class)
    )
    val tags: List<Tag>
)