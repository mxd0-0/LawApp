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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.Screen
import com.example.myapplication.presentation.viewModel.AuthViewModel

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = remember { AuthViewModel() },
) {
    val state = viewModel.uiState
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.isSignedIn) {
        if (state.isSignedIn) {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            // It's a good idea to translate Firebase errors too, but for now we show them as is.
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    // --- Validation function with Arabic error messages ---
    fun validateFields(): Boolean {
        if (email.isBlank()) {
            emailError = "لا يمكن أن يكون البريد الإلكتروني فارغًا"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "الرجاء إدخال بريد إلكتروني صالح"
        } else {
            emailError = null
        }

        if (password.isBlank()) {
            passwordError = "لا يمكن أن تكون كلمة المرور فارغة"
        } else {
            passwordError = null
        }

        return emailError == null && passwordError == null
    }

    // --- Wrap content in CompositionLocalProvider for RTL support ---
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.justice_scale_svgrepo_com),
                contentDescription = "شعار التطبيق",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text("البريد الإلكتروني") },
                isError = emailError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
            )
            emailError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it; passwordError = null },
                label = { Text("كلمة المرور") },
                isError = passwordError != null,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                    Icon(painter = painterResource(icon), contentDescription = if (passwordVisible) "إخفاء كلمة المرور" else "إظهار كلمة المرور", modifier = Modifier.clickable { passwordVisible = !passwordVisible })
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    if (validateFields()) { viewModel.signIn(email, password) }
                })
            )
            passwordError?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { if (validateFields()) { viewModel.signIn(email, password) } }
                ) { Text("تسجيل الدخول", fontWeight = FontWeight.Bold) }

                TextButton(onClick = { navController.navigate(Screen.SignUp.route) }) {
                    Text(buildAnnotatedString {
                        append("ليس لديك حساب؟ ")
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                            append("إنشاء حساب")
                        }
                    })
                }
            }
        }
    }
}