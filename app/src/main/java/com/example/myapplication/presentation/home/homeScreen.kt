package com.example.myapplication.presentation.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    AppTheme {
        val firestore = FirebaseFirestore.getInstance()
        val repository = LetterRepositoryImpl(firestore)
        val useCase = AddLetterUseCase(repository)
        val viewModel = LetterViewModel()
        var selectedCategory by remember { mutableStateOf<LetterCategory?>(null) }


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
                    Text(
                        text = "الصفحة الرئيسية",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        items(cards) { (icon, text) ->
                            homeCard(
                                icon = icon, text = text,
                                onClick = {

                                    selectedCategory = when (text) {
                                        "إستشارة مجانية" -> LetterCategory.FreeConsulting
                                        "طلب عارضة" -> LetterCategory.OccasionalRequest
                                        "إستشارة" -> LetterCategory.Consulting
                                        "طلب ملف" -> LetterCategory.FileRequest
                                        else -> null
                                    }
                                    isSheetOpen = selectedCategory != null

                                }, modifier = Modifier
                                    .padding(4.dp)
                                    .size(150.dp, 200.dp)
                            )
                        }
                    }
                }

                if (isSheetOpen && selectedCategory != null) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            isSheetOpen = false
                            selectedCategory = null
                        },
                        sheetState = sheetState,
                    ) {
                        LetterFormScreen(
                            formTitle = selectedCategory!!.name,
                            onLetterSent = {
                                scope.launch {
                                    sheetState.hide()
                                    isSheetOpen = false
                                    selectedCategory = null
                                }
                            },
                            viewModel = viewModel,
                            category = selectedCategory!!
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
    formTitle: String,
    category: LetterCategory,
    onLetterSent: () -> Unit,
) {
    var isPushed by remember { mutableStateOf(false) }
    var isSending by remember { mutableStateOf(false) }

    var letterTitle by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var uploadStatus by remember { mutableStateOf("Idle") }

    val scrollState = rememberScrollState()

    val addResult by viewModel.addResult.collectAsState()
    val scope = rememberCoroutineScope()

    // 🧼 Reset result on entry to avoid stale close
    LaunchedEffect(Unit) {
        viewModel.resetResult()
    }

    // ✅ React to submit result
    LaunchedEffect(addResult) {
        addResult?.onSuccess {
            uploadStatus = "✅ تم الإرسال بنجاح"
            isPushed = true
            isSending = false
            delay(1000)
            onLetterSent()
            viewModel.clearAddResult() // Clear result in ViewModel (you must implement this)
        }?.onFailure {
            uploadStatus = "❌ فشل الإرسال: ${it.message}"
            isSending = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = formTitle,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall
        )

        // عنوان الطلب
        Text("عنوان الطلب", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            shape = MaterialTheme.shapes.large,
            value = letterTitle,
            onValueChange = { letterTitle = it },
            modifier = Modifier.fillMaxWidth()
        )

        // الاسم الكامل
        Text("الاسم الكامل", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            shape = MaterialTheme.shapes.large,
            value = fullName,
            onValueChange = { fullName = it },
            modifier = Modifier.fillMaxWidth()
        )

        // رقم الهاتف
        Text("رقم الهاتف", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            shape = MaterialTheme.shapes.large,
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // الوصف
        Text("الوصف", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            shape = MaterialTheme.shapes.large,
            value = description,
            onValueChange = { description = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        AnimatedVisibility(
            visible = uploadStatus != "Idle",
            enter = fadeIn(tween(400)),
            exit = fadeOut(tween(400)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = uploadStatus,
                color = if (uploadStatus.startsWith("✅")) Color(0xFF4CAF50)
                else if (uploadStatus.startsWith("❌")) Color.Red
                else Color.Gray
            )
        }

        Button(
            onClick = {
                val userId = FirebaseAuth.getInstance().currentUser!!.uid
                val newLetter = Letter(
                    title = letterTitle,
                    description = description,
                    date = getCurrentDateString(),
                    userId = userId,
                    idLetter = UUID.randomUUID().toString(),
                    fullName = fullName,
                    phoneNumber = phoneNumber,
                    category = category
                )
                uploadStatus = "📤 جاري الإرسال..."
                isSending = true
                viewModel.addLetter(newLetter)
            },
            enabled = description.isNotBlank() && !isSending && !isPushed,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            if (isSending) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text(text = if (!isPushed) "إرسال الطلب" else "تم الإرسال")
            }
        }
    }
}



fun getCurrentDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
}
