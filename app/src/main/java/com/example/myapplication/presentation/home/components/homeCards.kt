package com.example.myapplication.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.CardBackground
import com.example.myapplication.ui.theme.AppTheme

@Composable
fun homeCard(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(4.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.White//Color(0x1064FFDA)
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(
            3.dp
        ),
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,


            ) {
            Icon(
                painter = painterResource(icon),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "chatIcon",
                modifier = Modifier.size(80.dp)
                    .padding(bottom = 6.dp)
            )

            Text(
                text,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun homeCardPrev() {
    AppTheme {
        homeCard(
            text = "طلب عارضة",
            icon = R.drawable.chat,
            modifier = Modifier.size(150.dp, 200.dp),
            onClick = {}
        )
    }
}