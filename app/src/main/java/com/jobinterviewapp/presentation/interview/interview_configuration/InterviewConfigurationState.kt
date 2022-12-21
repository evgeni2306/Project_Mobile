package com.jobinterviewapp.presentation.interview.interview_configuration

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto

data class InterviewConfigurationState(
    val fieldsOfActivity: List<FieldOfActivityDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)