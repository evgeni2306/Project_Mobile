package com.jobinterviewapp.presentation.interview.interview_configuration

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.remote.dto.SavedProfessionDto

data class SavedProfessionsState(
    val savedProfessions: List<SavedProfessionDto> = emptyList(),
    val userKey: String? = null,
    val error: UiText? = null,
    val isLoading: Boolean = false,
)