package com.example.myapplication.presentation.history.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun historyItemCard(modifier: Modifier = Modifier) {
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(modifier= Modifier.fillMaxWidth()) {
            Text("التاريخ", modifier = Modifier.weight(1f))
            Text()
        }

    }

}