// UserWithExpenses.kt - 1-n ilişki için
package com.example.expensetracker.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.expensetracker.data.model.User
import com.example.expensetracker.data.model.Expense

data class UserWithExpenses(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val expenses: List<Expense>
)