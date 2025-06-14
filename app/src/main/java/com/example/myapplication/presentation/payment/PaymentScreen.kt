package com.example.myapplication.presentation.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AppTheme


@Composable
fun PaymentsScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.comming_soon),
            contentDescription = null,
            Modifier.size(250.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(25.dp))
        Text("قريباً",
           style =  MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary)
    }

}

@Composable
@Preview(showSystemUi = true)
fun PaymentsScreenPrev() {
    AppTheme {
        PaymentsScreen()
    }

}
