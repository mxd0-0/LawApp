package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.LetterDto
import com.example.myapplication.domain.model.Letter

fun Letter.toDto(): LetterDto = LetterDto(
    title = this.title,
    description = this.description,
    date = this.date,
    userId = this.userId
)
