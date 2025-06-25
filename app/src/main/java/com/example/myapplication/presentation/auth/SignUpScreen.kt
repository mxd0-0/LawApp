package com.example.myapplication.presentation.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.Screen
import com.example.myapplication.presentation.viewModel.AuthViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = remember { AuthViewModel() }
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = uiState.isSignedIn) {
        if (uiState.isSignedIn) {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.SignUp.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    LaunchedEffect(key1 = uiState.error) {
        uiState.error?.let { Toast.makeText(context, it, Toast.LENGTH_LONG).show() }
    }

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }
    var telephoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var passwordVisible by remember { mutableStateOf(false) }

    // --- Validation function with Arabic error messages ---
    fun validateFields(): Boolean {
        nameError = if (name.isBlank()) "لا يمكن أن يكون الاسم الأول فارغًا" else null
        lastNameError = if (lastName.isBlank()) "لا يمكن أن يكون اسم العائلة فارغًا" else null
        telephoneError = if (telephone.isBlank()) "لا يمكن أن يكون رقم الهاتف فارغًا" else null

        if (email.isBlank()) {
            emailError = "لا يمكن أن يكون البريد الإلكتروني فارغًا"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "الرجاء إدخال بريد إلكتروني صالح"
        } else {
            emailError = null
        }

        if (password.isBlank()) {
            passwordError = "لا يمكن أن تكون كلمة المرور فارغة"
        } else if (password.length < 8) {
            passwordError = "يجب أن تتكون كلمة المرور من 8 أحرف على الأقل"
        } else {
            passwordError = null
        }

        return nameError == null && lastNameError == null && telephoneError == null && emailError == null && passwordError == null
    }

    // --- The key for RTL: Wrap content in CompositionLocalProvider ---
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "إنشاء حساب", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; nameError = null },
                label = { Text("الاسم الأول") },
                modifier = Modifier.fillMaxWidth(),
                isError = nameError != null,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            nameError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start) }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it; lastNameError = null },
                label = { Text("اسم العائلة") },
                modifier = Modifier.fillMaxWidth(),
                isError = lastNameError != null,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            lastNameError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start) }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = telephone,
                onValueChange = { telephone = it; telephoneError = null },
                label = { Text("رقم الهاتف") },
                modifier = Modifier.fillMaxWidth(),
                isError = telephoneError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            telephoneError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start) }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text("البريد الإلكتروني") },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            emailError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start) }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = { Text("كلمة المرور") },
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError != null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                    Icon(painter = painterResource(id = icon), contentDescription = if (passwordVisible) "إخفاء كلمة المرور" else "إظهار كلمة المرور", modifier = Modifier.clickable { passwordVisible = !passwordVisible })
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (validateFields()) { viewModel.signUp(name, lastName, telephone, email, password) }
                })
            )
            passwordError?.let { Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start) }

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { if (validateFields()) { viewModel.signUp(name, lastName, telephone, email, password) } },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) { Text(text = "إنشاء حساب", fontWeight = FontWeight.Bold) }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { navController.popBackStack() }) { Text("لديك حساب بالفعل؟ تسجيل الدخول") }
        }
    }
}