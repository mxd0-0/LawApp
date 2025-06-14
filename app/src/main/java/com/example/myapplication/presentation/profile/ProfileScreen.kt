package com.example.myapplication.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.Screen
import com.example.myapplication.presentation.viewModel.AuthViewModel


@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: AuthViewModel = remember { AuthViewModel() },
) {
    val state = viewModel.uiState

    // ✅ Check sign-in status once when the screen appears
    LaunchedEffect(Unit) {
        viewModel.checkSignInStatus()
    }

    // 🔁 If user is signed out, redirect to auth screen
    LaunchedEffect(state.isSignedIn) {
        if (!state.isSignedIn) {
            navController.navigate(Screen.Auth.route) {
                popUpTo(Screen.Main.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar image
        Image(
            painter = painterResource(id = R.drawable.ic_justice),
            contentDescription = "الصورة الشخصية",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))



        Spacer(modifier = Modifier.height(32.dp))

        // Sign out Button
        Button(
            onClick = { viewModel.signOut() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("تسجيل الخlogout", color = MaterialTheme.colorScheme.onError)
        }
    }
}
