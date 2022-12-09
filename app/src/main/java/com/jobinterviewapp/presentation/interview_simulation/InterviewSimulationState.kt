package com.jobinterviewapp.presentation.interview_simulation

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.remote.dto.TaskDto

data class InterviewSimulationState(
    val error: UiText? = null,
    val interviewId: Int? = null,
    val currentTaskNumber: Int = 1,
    val currentTask: TaskDto? = null,
    val userKey: String? = null,
)