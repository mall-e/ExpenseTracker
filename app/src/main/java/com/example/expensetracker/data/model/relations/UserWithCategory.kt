// ExpenseWithCategory.kt - 1-1 ilişki için
package com.example.expensetracker.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.model.Category

data class ExpenseWithCategory(
    @Embedded val expense: Expense,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val category: Category
)