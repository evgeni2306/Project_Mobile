package com.jobinterviewapp.domain.models

data class InterviewResult(
    val countRight: Int,
    val countWrong: Int,
    val wrongQuestions: List<Task>
)