import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.model.Category
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.viewmodel.MainViewModel
import androidx.compose.foundation.lazy.items

// ui/screens/SummaryScreen.kt
@Composable
fun SummaryScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val userWithExpenses by viewModel.currentUserWithExpenses.collectAsState(initial = null)
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    Column(modifier = modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Bu Ayki Toplam",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "₺${userWithExpenses?.expenses?.sumOf { it.amount } ?: 0.0}",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategorySummaryCard(category = category, expenses = userWithExpenses?.expenses ?: emptyList())
            }
        }
    }
}

@Composable
fun CategorySummaryCard(category: Category, expenses: List<Expense>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "₺${expenses.filter { it.categoryId == category.categoryId }.sumOf { it.amount }}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}