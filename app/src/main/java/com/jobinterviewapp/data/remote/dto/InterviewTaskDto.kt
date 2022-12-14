package com.jobinterviewapp.data.remote.dto

data class InterviewTaskDto(
    val taskId: Int,
    val question: String,
    val answer: String,
    val category: String,
    val questionId: Int,
    val isFavorite: Int,
    val favoriteId: Int,
)