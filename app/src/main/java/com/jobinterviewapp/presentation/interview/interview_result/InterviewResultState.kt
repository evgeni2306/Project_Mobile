package com.jobinterviewapp.presentation.interview.interview_result

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.domain.models.Task

data class InterviewResultState(
    val userKey: String? = null,
    val error: UiText? = null,
    val wrongAnswers: List<Task> = emptyList(),
    val rightAnswersCount: Int? = null,
    val wrongAnswersCount: Int? = null,
    val rightAnswersPercentage: Float? = null,
    val answersCount: Int? = null,
    val professionId: Int? = null,
    val isLoading: Boolean = false,
)