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

    private val repository = AuthRepositoryImpl()
    private val useCase = AuthUseCase(repository)

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = useCase.signIn(email, password)
            uiState = if (result.isSuccess) {
                uiState.copy(isSignedIn = true, isLoading = false, error = null)
            } else {
                uiState.copy(error = result.exceptionOrNull()?.message, isLoading = false)
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = useCase.signUp(email, password)
            uiState = if (result.isSuccess) {
                uiState.copy(isSignedIn = true, isLoading = false, error = null)
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
