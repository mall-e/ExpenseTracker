package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.expensetracker.ui.screens.MainScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Test kullanıcısı oluştur
        viewModel.addUser("Test Kullanıcı", "test@test.com")
        // İlk kullanıcıyı seç
        viewModel.setCurrentUser(1)

        setContent {
            ExpenseTrackerTheme {
                MainScreen(viewModel)
            }
        }
    }
}