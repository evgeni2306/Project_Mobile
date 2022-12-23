package com.jobinterviewapp.domain.models

data class InterviewTask(
    val taskId: Int,
    val question: String,
    val answer: String,
    val category: String,
    val questionId: Int,
    val isFavorite: Boolean,
    val favoriteId: Int?,
)