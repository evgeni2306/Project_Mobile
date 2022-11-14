package com.jobinterviewapp.presentation.interview

import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto

data class InterviewState(
    val fieldsOfActivity: List<FieldOfActivityDto> = emptyList(),
    val isLoading: Boolean = false,
)