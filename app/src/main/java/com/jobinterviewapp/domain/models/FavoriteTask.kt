package com.jobinterviewapp.domain.models

data class FavoriteTask(
    val isSelected: Boolean = false,
    val favoriteId: Int,
    val question: String,
    val answer: String,
    val category: String,
)