package com.example.myapplication.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.auth.AuthScreen
import com.example.myapplication.presentation.history.HistoryScreen
import com.example.myapplication.presentation.home.HomeScreen
import com.example.myapplication.presentation.payment.PaymentsScreen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object History : Screen("history")
    object Profile : Screen("profile")
}



@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val shouldShowBottomBar = currentRoute != Screen.Auth.route

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Auth.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Auth.route) {
                AuthScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.History.route) {
                HistoryScreen()
            }
            composable(Screen.Profile.route) {
                PaymentsScreen()
            }
        }
    }
}
