package com.example.myapplication.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.auth.AuthScreen
import com.example.myapplication.presentation.home.HomeScreen
import com.example.myapplication.presentation.payment.PaymentsScreen
import com.example.myapplication.presentation.profile.ProfileScreen

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object  Main : Screen("main")
    object Home : Screen("home")
    object History : Screen("history")
    object Profile : Screen("profile")
}



@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Auth.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Main.route) {
                Main(navController)
            }
            composable(Screen.Auth.route) {
                AuthScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.History.route) {

            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
        }
    }
}
