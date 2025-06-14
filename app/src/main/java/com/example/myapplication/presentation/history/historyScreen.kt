package com.example.myapplication.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.history.components.HistoryItemCard
import com.example.myapplication.presentation.viewModel.LetterViewModel

@Composable
fun HistoryScreen(viewModel: LetterViewModel) {
    val letters = viewModel.userLetters.value

    LaunchedEffect(Unit) {
        viewModel.fetchUserLetters()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "📜 سجل الطلبات",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (letters.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("لا يوجد طلبات حتى الآن.")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(letters) { letter ->
                    HistoryItemCard(
                        date = letter.date,
                        title = letter.title,
                        description = letter.description
                    )
                }
            }
        }
    }
}
