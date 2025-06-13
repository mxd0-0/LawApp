package com.example.myapplication.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R


@Composable
fun BottomNavigationBar(navController: NavController) {
    // Track current destination from NavController
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) },
            icon = {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.outline_home_24),
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp),
                    tint = if (currentRoute == Screen.Home.route)Color(0xFF4CAF50) else Color.Gray
                )
            },
            label = {
                Text(
                    "Home",
                    color = if (currentRoute == Screen.Home.route) Color(0xFF4CAF50) else Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.History.route,
            onClick = { navController.navigate(Screen.History.route) },
            icon = {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.outline_history_24),
                    contentDescription = "History",
                    tint = if (currentRoute == Screen.History.route) Color(0xFF4CAF50) else Color.Gray
                )
            },
            label = {
                Text(
                    "History",
                    color = if (currentRoute == Screen.History.route) Color(0xFF4CAF50) else Color.Gray
                )
            }
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = { navController.navigate(Screen.Profile.route) },
            icon = {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.outline_payments_24),
                    contentDescription = "Payment",
                    modifier = Modifier.size(38.dp),
                    tint = if (currentRoute == Screen.Profile.route) Color(0xFF4CAF50) else Color.Gray
                )
            },
            label = {
                Text(
                    "Payment",
                    color = if (currentRoute == Screen.Profile.route) Color(0xFF4CAF50) else Color.Gray
                )
            }
        )
    }
}

@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .then(Modifier.clickable(onClick = onClick)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon()
        }

        label()
    }
}


