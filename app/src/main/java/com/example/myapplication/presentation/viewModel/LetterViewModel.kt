package com.example.myapplication.presentation.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.useCase.AddLetterUseCase
import com.example.myapplication.domain.useCase.GetUserLettersUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class LetterViewModel(
    private val addLetterUseCase: AddLetterUseCase,
    private val getUserLettersUseCase: GetUserLettersUseCase
) : ViewModel() {

    private val _addResult = MutableStateFlow<Result<Unit>?>(null)
    val addResult: StateFlow<Result<Unit>?> = _addResult

    private val _userLetters = mutableStateOf<List<Letter>>(emptyList())
    val userLetters: State<List<Letter>> = _userLetters



    fun fetchUserLetters() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        viewModelScope.launch {
            val letters = getUserLettersUseCase(userId) // just get the list
            _userLetters.value = letters
        }
    }


    fun addLetter(letter: Letter) {
        addLetterUseCase(
            letter,
            onSuccess = {
                _addResult.value = Result.success(Unit)
            },
            onError = {
                _addResult.value = Result.failure(it)
            }
        )
    }
}
