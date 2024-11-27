// ui/screens/StatisticsScreen.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import com.example.expensetracker.data.model.Category
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.viewmodel.MainViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val userWithExpenses by viewModel.currentUserWithExpenses.collectAsState(initial = null)
    val categories by viewModel.categories.collectAsState(initial = emptyList())

    Column(modifier = modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
        ) {
            PieChart(
                expenses = userWithExpenses?.expenses ?: emptyList(),
                categories = categories
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(categories) { category ->
                val categoryExpenses = userWithExpenses?.expenses
                    ?.filter { it.categoryId == category.categoryId } ?: emptyList()
                val total = categoryExpenses.sumOf { it.amount }
                val percentage = if (userWithExpenses?.expenses?.isNotEmpty() == true) {
                    (total / userWithExpenses?.expenses?.sumOf { it.amount }!!) * 100
                } else 0.0

                CategoryStatCard(
                    category = category,
                    total = total,
                    percentage = percentage
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun PieChart(expenses: List<Expense>, categories: List<Category>) {
    if (expenses.isEmpty()) return

    val total = expenses.sumOf { it.amount }
    val colorList = listOf(
        Color(0xFF2196F3), Color(0xFF4CAF50), Color(0xFFFFC107),
        Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF00BCD4)
    )

    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        var startAngle = 0f
        categories.forEachIndexed { index, category ->
            val categoryExpenses = expenses.filter { it.categoryId == category.categoryId }
            val sweepAngle = (categoryExpenses.sumOf { it.amount } / total * 360).toFloat()

            drawArc(
                color = colorList[index % colorList.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
private fun CategoryStatCard(
    category: Category,
    total: Double,
    percentage: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₺$total",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "%.1f%%".format(percentage),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}