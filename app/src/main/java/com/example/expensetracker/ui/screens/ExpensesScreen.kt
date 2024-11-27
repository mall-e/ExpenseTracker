import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.model.Category
import com.example.expensetracker.ui.viewmodel.MainViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.expensetracker.data.model.Expense


// ui/screens/ExpensesScreen.kt
@Composable
fun ExpensesScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val userWithExpenses by viewModel.currentUserWithExpenses.collectAsState(initial = null)
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    Column(modifier = modifier.fillMaxSize()) {
        // Filtreleme Kartı
        FilterCard(
            categories = categories,
            onFilterSelected = { /* TODO */ }
        )

        // Harcama Listesi
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            userWithExpenses?.expenses?.let { expenses ->
                items(expenses) { expense ->
                    ExpenseCard(
                        expense = expense,
                        category = categories.find { it.categoryId == expense.categoryId },
                        onExpenseClick = { /* TODO: Detay ekranına git */ },
                        onDeleteClick = { viewModel.deleteExpense(expense) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterCard(
    categories: List<Category>,
    onFilterSelected: (Int?) -> Unit
) {
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Filtrele",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedCategoryId == null,
                        onClick = {
                            selectedCategoryId = null
                            onFilterSelected(null)
                        },
                        label = { Text("Tümü") }
                    )
                }
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategoryId == category.categoryId,
                        onClick = {
                            selectedCategoryId = category.categoryId
                            onFilterSelected(category.categoryId)
                        },
                        label = { Text(category.name) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpenseCard(
    expense: Expense,
    category: Category?,
    onExpenseClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onExpenseClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = expense.description,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category?.name ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₺${expense.amount}",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Sil",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}