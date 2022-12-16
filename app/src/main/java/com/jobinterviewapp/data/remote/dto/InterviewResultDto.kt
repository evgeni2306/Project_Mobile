package com.jobinterviewapp.data.remote.dto

data class InterviewResultDto(
    val countRight: Int,
    val countWrong: Int,
    val wrongQuestions: List<TaskDto>
)