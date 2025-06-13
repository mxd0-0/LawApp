package com.example.myapplication.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.data.repository.LetterRepositoryImpl
import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.model.LetterCategory
import com.example.myapplication.domain.useCase.AddLetterUseCase
import com.example.myapplication.presentation.home.components.homeCard
import com.example.myapplication.presentation.viewModel.LetterViewModel
import com.example.myapplication.ui.theme.AppTheme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    AppTheme {
        val firestore = FirebaseFirestore.getInstance()
        val repository = LetterRepositoryImpl(firestore)
        val useCase = AddLetterUseCase(repository)
        val viewModel = LetterViewModel(useCase)

        val cards = listOf(
            Pair(R.drawable.chat, "إستشارة مجانية"),
            Pair(R.drawable.chat, "طلب عارضة"),
            Pair(R.drawable.chat, "إستشارة"),
            Pair(R.drawable.ic_justice, "طلب ملف")
        )

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true, confirmValueChange = { true })
        val scope = rememberCoroutineScope()
        var isSheetOpen by remember { mutableStateOf(false) }

        Scaffold { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(5.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        items(cards) { (icon, text) ->
                            homeCard(
                                icon = icon, text = text, onClick = {
                                    when (text) {
                                        "إستشارة مجانية" -> isSheetOpen = true
                                        "طلب عارضة" -> isSheetOpen = true
                                        "إستشارة" -> isSheetOpen = true
                                        "طلب ملف" -> isSheetOpen = true
                                    }
                                }, modifier = Modifier
                                    .padding(4.dp)
                                    .size(150.dp, 200.dp)
                            )
                        }
                    }
                }

                if (isSheetOpen) {
                    ModalBottomSheet(
                        onDismissRequest = { isSheetOpen = false },
                        sheetState = sheetState,
                    ) {
                        LetterFormScreen(
                            title = "طلب عارضة", onLetterSent = {
                                scope.launch {
                                    sheetState.hide()
                                    isSheetOpen = false
                                }
                            }, viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun LetterFormScreen(
    viewModel: LetterViewModel,
    title: String, // e.g., "طلب عارضة"
    onLetterSent: () -> Unit, // callback after successful send
) {

    var isPushed by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    //val scope = rememberCoroutineScope()
    // NEw

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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column {
            Text(
                text = "عنوان الطلب",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                shape = MaterialTheme.shapes.large,
                value = title,
                onValueChange = { title = it },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Column {
            Text(
                text = "الاسم كامل",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                shape = MaterialTheme.shapes.large,
                value = fullName,
                onValueChange = { fullName = it },

                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Column {
            Text(
                text = "رقم الهاتف",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                shape = MaterialTheme.shapes.large,
                value = number,
                onValueChange = { number = it },
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Column {
            Text(
                text = "الوصف",
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                shape = MaterialTheme.shapes.large,
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            )
        }

        Button(
            onClick = {
                val NewLetter = Letter(
                    title = title,
                    description = description,
                    date = getCurrentDateString(), // Example date, replace with actual date logic
                    userId = "utilisateur_213",
                    idLetter = UUID.randomUUID().toString(),
                    fullName = fullName,
                    phoneNumber = number,
                    category = LetterCategory.Consulting // Replace with actual category logic
                )
                uploadStatus = "Uploading..."
                viewModel.addLetter(NewLetter)
            },
            enabled = description.isNotBlank() && !isSending && !isPushed,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            if (isSending) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text(if (!isPushed) "Push Letter" else "Already Pushed")
            }
        }
    }
}

fun getCurrentDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}
