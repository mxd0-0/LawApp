package com.example.myapplication.presentation.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun HistoryItemCard(
    date: String,
    title: String,
    description: String,
    lawyerAnswer: String?,
    modifier: Modifier = Modifier,
) {
    var showAnswer by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { showAnswer = !showAnswer },
                onLongClick = { showAnswer = !showAnswer }
            )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )

                if (!lawyerAnswer.isNullOrBlank()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "تم الرد",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color(0xFF868282), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Description (truncated)
            Text(
                truncateArabicText(description),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.W200,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                maxLines = 1
            )

            // Hint to guide the user
            Text(
                text = "اضغط لعرض رد المحامي",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                textAlign = TextAlign.Center
            )

            // Lawyer's answer (toggle visibility)
            if (showAnswer) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (!lawyerAnswer.isNullOrBlank())
                        "رد المحامي: $lawyerAnswer"
                    else
                        "لا يوجد رد من المحامي حتى الآن.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
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

