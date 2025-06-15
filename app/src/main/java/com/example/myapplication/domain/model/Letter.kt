package com.example.myapplication.domain.model



data class Letter(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val userId: String = "",
    val idLetter: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val category: LetterCategory = LetterCategory.FreeConsulting,
    val lawyerAnswer: String = "", // Add this
    val lawyerId: String = ""      // Add this
)

