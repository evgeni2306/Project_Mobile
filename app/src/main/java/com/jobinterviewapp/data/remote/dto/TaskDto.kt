package com.jobinterviewapp.data.remote.dto

data class TaskDto(
    val taskId: Int,
    val question: String,
    val answer: String,
    val category: String,
    val interviewId: Int,
)