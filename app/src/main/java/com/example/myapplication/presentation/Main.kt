package com.example.myapplication.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.domain.model.BottomNavigationItem
import com.example.myapplication.presentation.history.HistoryScreen
import com.example.myapplication.presentation.home.HomeScreen
import com.example.myapplication.presentation.payment.PaymentsScreen
import com.example.myapplication.presentation.profile.ProfileScreen
import com.example.myapplication.presentation.viewModel.LetterViewModel

@Composable
fun Main(    navController: NavController,
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon =R.drawable.home_solid ,
            unselectedIcon =R.drawable.home ,
        ),
        BottomNavigationItem(
            title = "History",
            selectedIcon = R.drawable.time_solid,
            unselectedIcon = R.drawable.time,
        ),
        BottomNavigationItem(
            title = "Payments",
            selectedIcon = R.drawable.receiptsolid,
            unselectedIcon = R.drawable.receipt,
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = R.drawable.user_solid,
            unselectedIcon = R.drawable.user,
        ),
    )
    var bottomNavState by rememberSaveable {
        mutableIntStateOf(0)
    }
    val viewModel: LetterViewModel = viewModel()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp
                    )
                )
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = bottomNavState == index
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            indicatorColor = Color.Transparent,

                            ), icon = {
                            Icon(
                                painter = painterResource(item.selectedIcon),
                                contentDescription = item.title,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary
                                else Color.Gray,
                            )
                        }, label = {
                            Text(
                                item.title,
                                color = if (isSelected) MaterialTheme.colorScheme.primary
                                else Color.Gray
                            )
                        }, selected = bottomNavState == index, onClick = {
                            bottomNavState = index
                        }, alwaysShowLabel = isSelected
                    )
                }
            }
        }, content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
                    .safeContentPadding()
            ) {
                // Here you can place your content based on the bottomNavState
                when (bottomNavState) {
                    0 -> HomeScreen() // Replace with HomeScreen()
                    1 -> HistoryScreen(viewModel = viewModel)
                    2 -> PaymentsScreen()
                    3-> ProfileScreen(navController)
                }
                // Content based on bottomNavState
            }

        }
    )
}