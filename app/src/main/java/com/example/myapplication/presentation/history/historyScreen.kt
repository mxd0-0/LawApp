package com.example.myapplication.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.HistoryItem
import com.example.myapplication.presentation.history.components.historyItemCard
import com.example.myapplication.presentation.viewModel.LetterViewModel
import com.example.myapplication.ui.theme.AppTheme

@Composable
fun HistoryScreen(viewModel: LetterViewModel) {
    // Observe the list of letters
    val letters = viewModel.userLetters.value

    // Trigger fetch only once when screen appears
    LaunchedEffect(Unit) {
        viewModel.fetchUserLetters()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "📌 العنوان: ${letter.title}", style = MaterialTheme.typography.titleMedium)
                            Text(text = "📅 التاريخ: ${letter.date}")
                            Text(text = "📞 الهاتف: ${letter.phoneNumber}")
                            Text(text = "📝 الوصف: ${letter.description}")
                        }
                    }
                }
            }
        }
    }
}


