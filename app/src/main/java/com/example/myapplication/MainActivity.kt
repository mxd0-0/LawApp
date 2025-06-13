package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.repository.LetterRepositoryImpl
import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.model.LetterCategory
import com.example.myapplication.domain.useCase.AddLetterUseCase
import com.example.myapplication.presentation.viewModel.LetterViewModel
import com.example.myapplication.ui.theme.AppTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)


        val firestore = FirebaseFirestore.getInstance()
        val repository = LetterRepositoryImpl(firestore)
        val useCase = AddLetterUseCase(repository)
        val viewModel = LetterViewModel(useCase)

        setContent {

                UploadLetterScreen(viewModel)

        }
    }
}


@Composable
fun UploadLetterScreen(viewModel: LetterViewModel) {
    val scope = rememberCoroutineScope()
    var uploadStatus by remember { mutableStateOf("Idle") }
    val addResult by viewModel.addResult.collectAsState()

    // Observe result
    LaunchedEffect(addResult) {
        addResult?.onSuccess {
            uploadStatus = "✅ Success"
        }?.onFailure {
            uploadStatus = "❌ Failed: ${it.message}"
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Upload a Test Letter to Firestore")
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val algerianLetter = Letter(
                title = "Demande de consultation juridique",
                description = "Je souhaite obtenir des conseils juridiques concernant un litige foncier à Blida.",
                date = "2025-06-13",
                userId = "utilisateur_213",
                idLetter = UUID.randomUUID().toString(),
                fullName = "Amine Bensalah",
                phoneNumber = "+213661234567",
                category = LetterCategory.Consulting
            )

            uploadStatus = "Uploading..."
            viewModel.addLetter(algerianLetter)
        }) {
            Text("Upload Letter")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Status: $uploadStatus")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
    }
}