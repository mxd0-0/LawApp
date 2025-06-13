package com.example.myapplication.presentation.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.HistoryItem
import com.example.myapplication.presentation.history.components.historyItemCard
import com.example.myapplication.ui.theme.AppTheme

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {
    val dummyData = listOf(
        HistoryItem("21/03/2024", "طلب قمح", "وصف قصير للطلب الأولوصف قصير للطلب الأولوصف قصير للطلب الأولوصف قصير للطلب الأولوصف الثاني الثاني قصير للطلب الأول"),
        HistoryItem("22/03/2024", "طلب شعير", "وصف قصير للطلب الثاني الثاني الثاني"),
        HistoryItem("22/03/2024", "طلب شعير", " الثانيالثاني وصف قصير للطلب الثاني"),
        HistoryItem("23/03/2024", "طلب ذرة", "وصف قصير  الثاني  الثاني للطلب الثالث"),
        HistoryItem("24/03/2024", "طلب أرز", "وصف قصير الثانيالثاني للطلب الرابع")
    )

    LazyColumn(modifier = modifier) {
        items(dummyData) { item ->
            historyItemCard(
                modifier = Modifier.padding(vertical = 8.dp),
                date = item.date,
                title = item.text,
                description = item.description
            )
        }
    }


}

@Composable
@Preview
fun HistoryScreenPrev() {
    AppTheme {
        HistoryScreen()
    }
}
