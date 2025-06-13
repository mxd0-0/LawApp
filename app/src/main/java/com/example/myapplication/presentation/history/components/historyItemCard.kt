package com.example.myapplication.presentation.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.AppTheme


@Composable
fun historyItemCard(
    date: String,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    date, modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                truncateArabicText(description),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.W200,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }
    }
}

fun truncateArabicText(text: String): String {
    val words = text.split(" ")
    return if (words.size > 4) {
        words.take(4).joinToString(prefix = "...")
    } else {
        text
    }
}

@Preview
@Composable
private fun historyItemCardPrev() {
    AppTheme {
        historyItemCard(
            date = "21/03/2024",
            title = "طلب قمح",
            description = "وصف قصير للطلب الثاني",
            modifier = Modifier.padding(16.dp)
        )
    }
}