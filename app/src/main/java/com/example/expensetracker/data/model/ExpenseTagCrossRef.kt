// ExpenseTagCrossRef.kt
package com.example.expensetracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "expense_tag_cross_ref",
    primaryKeys = ["expenseId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = Expense::class,
            parentColumns = ["expenseId"],
            childColumns = ["expenseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["tagId"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExpenseTagCrossRef(
    val expenseId: Int,
    val tagId: Int
)