package com.example.myapplication.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.presentation.home.components.homeCard

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val cards = listOf(
        Pair(R.drawable.chat, "إستشارة مجانية"),
        Pair(R.drawable.chat, "طلب عارضة"),
        Pair(R.drawable.chat, "إستشارة"),
        Pair(R.drawable.ic_justice, "طلب ملف")
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(5.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(cards) { (icon, text) ->
                homeCard(
                    icon = icon,
                    text = text,
                    onClick = {},
                    modifier = Modifier
                        .padding(4.dp)
                        .size(150.dp, 200.dp)
                )
            }
        }
    }

}

@Preview
@Composable
private fun homeScreenPrev() {
    HomeScreen()

}