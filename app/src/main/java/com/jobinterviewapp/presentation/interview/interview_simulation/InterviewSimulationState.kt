package com.jobinterviewapp.presentation.interview.interview_simulation

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.remote.dto.InterviewTaskDto

data class InterviewSimulationState(
    val error: UiText? = null,
    val interviewId: Int? = null,
    val currentTaskNumber: Int = 1,
    val currentTask: InterviewTaskDto? = null,
    val userKey: String? = null,
    val isEndOfInterview: Boolean = false,
    val professionId: Int? = null,
)