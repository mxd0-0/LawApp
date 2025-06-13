package com.example.myapplication.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
     val email = "test2_good@gmail.com"
     val password = "T1t1t1t1t1t1t1"
    val coroutineScope = rememberCoroutineScope()

    val authRepository = AuthRepository()

    var isLoggedIn by rememberSaveable {
        mutableStateOf(authRepository.isLoggedIn())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoggedIn) {
            OutlinedButton(onClick = {
                authRepository.logout()
                isLoggedIn = false
            }) {
                Text(text = "Log Out")
            }

        } else {

            OutlinedButton(onClick = {
                coroutineScope.launch {
                    isLoggedIn = authRepository.register(email, password)
                }
            }) {
                Text(text = "Register")
            }

            Spacer(modifier = Modifier.height(50.dp))

            OutlinedButton(onClick = {
                coroutineScope.launch {
                    isLoggedIn = authRepository.login(email, password)
                }
            }) {
                Text(text = "Log In")
            }
        }
    }
}




@Composable
@Preview
fun AuthScreenPrev() {
    AuthScreen()
}
