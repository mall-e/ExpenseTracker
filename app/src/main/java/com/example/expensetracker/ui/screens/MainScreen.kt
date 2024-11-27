// ui/screens/MainScreen.kt
package com.example.expensetracker.ui.screens

import ExpensesScreen
import StatisticsScreen
import SummaryScreen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.model.Category
import com.example.expensetracker.ui.viewmodel.MainViewModel
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    var showAddExpenseDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Harcama Takibi") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, "Ana Sayfa") },
                    label = { Text("Özet") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, "Harcamalar") },
                    label = { Text("Harcamalar") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.DateRange, "İstatistik") },
                    label = { Text("İstatistik") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddExpenseDialog = true }) {
                Icon(Icons.Default.Add, "Harcama Ekle")
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> SummaryScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel
            )
            1 -> ExpensesScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel
            )
            2 -> StatisticsScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = viewModel
            )
        }
    }
    if (showAddExpenseDialog) {
        AddExpenseDialog(
            categories = viewModel.categories.collectAsState(initial = emptyList()).value,
            onDismiss = { showAddExpenseDialog = false },
            onAddExpense = { amount, description, categoryId ->
                viewModel.addExpense(amount, description, categoryId)
                showAddExpenseDialog = false
            }
        )
    }
}

@Composable
private fun AddExpenseDialog(
    categories: List<Category>,
    onDismiss: () -> Unit,
    onAddExpense: (amount: Double, description: String, categoryId: Int) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Yeni Harcama") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Tutar") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Açıklama") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Kategori", style = MaterialTheme.typography.bodyMedium)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategoryId == category.categoryId,
                            onClick = { selectedCategoryId = category.categoryId },
                            label = { Text(category.name) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    amount.toDoubleOrNull()?.let { amountDouble ->
                        selectedCategoryId?.let { categoryId ->
                            if (description.isNotEmpty()) {
                                onAddExpense(amountDouble, description, categoryId)
                            }
                        }
                    }
                }
            ) {
                Text("Ekle")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}