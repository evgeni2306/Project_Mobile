package com.jobinterviewapp.data.remote.dto

data class FavoriteTaskDto(
    val favoriteId: Int,
    val question: String,
    val answer: String,
    val category: String,
)