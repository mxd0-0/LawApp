package com.example.myapplication.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.presentation.home.components.homeCard

@Composable
fun homeScreen(modifier: Modifier = Modifier) {
    val cards = listOf(
        Pair(R.drawable.chat, "اين قضياتي"),
        Pair(R.drawable.chat, "طلب عارضة"),
        Pair(R.drawable.chat, "تقديم طلب"),
        Pair(R.drawable.chat, "طلب ملف")
    )
    Scaffold(
        bottomBar = {

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(16.dp)
            ) {
                items(cards) { (icon, text) ->
                    homeCard(
                        icon = icon,
                        text = text,
                        onClick = {},
                        modifier = Modifier
                            .padding(8.dp)
                            .size(150.dp, 200.dp)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun homeScreenPrev() {
    homeScreen()

}