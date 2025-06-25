package com.example.myapplication.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.AuthRepositoryImpl
import com.example.myapplication.domain.useCase.AuthUseCase
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    // Note: In a real app, you would use Dependency Injection (e.g., Hilt) here.
    private val repository = AuthRepositoryImpl()
    private val useCase = AuthUseCase(repository)

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = useCase.signIn(email, password)
            uiState = if (result.isSuccess) {
                uiState.copy(isSignedIn = true, isLoading = false)
            } else {
                uiState.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }

    // Updated signUp function
    fun signUp(name: String, lastName: String, telephone: String, email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            // Basic validation
            if (name.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                uiState = uiState.copy(error = "All fields must be filled", isLoading = false)
                return@launch
            }

            val result = useCase.signUp(name, lastName, telephone, email, password)
            uiState = if (result.isSuccess) {
                // After successful sign-up, the user is automatically signed in
                uiState.copy(isSignedIn = true, isLoading = false)
            } else {
                uiState.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            useCase.signOut()
            uiState = uiState.copy(isSignedIn = false)
        }
    }

    fun checkSignInStatus() {
        uiState = uiState.copy(isSignedIn = useCase.isUserSignedIn())
    }
}

data class AuthUiState(
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)