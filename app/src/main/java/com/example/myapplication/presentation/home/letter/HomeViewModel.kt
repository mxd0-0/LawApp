package com.example.myapplication.presentation.home.letter
/*
import com.example.myapplication.data.remote.fireBase.FirestoreService

@HiltViewModel
class LetterViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val authService: AuthService
) : ViewModel() {

    private val _isSending = MutableStateFlow(false)
    val isSending: StateFlow<Boolean> = _isSending.asStateFlow()

    fun sendLetter(
        title: String,
        description: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isSending.value = true

            val userId = authService.getCurrentUserId()
            val letter = mapOf(
                "title" to title,
                "description" to description,
                "date" to getCurrentDate(),
                "userId" to userId
            )

            try {
                firestoreService.sendConsultation(letter)
                onSuccess()
            } catch (e: Exception) {
                Log.e("LetterViewModel", "Error sending letter", e)
            } finally {
                _isSending.value = false
            }
        }
    }

    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }
}
*/