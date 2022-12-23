package com.jobinterviewapp.domain.models

data class Task(
    val question: String,
    val answer: String,
    val category: String,
    val questionId: Int,
    val isFavorite: Boolean,
    val favoriteId: Int?,
)