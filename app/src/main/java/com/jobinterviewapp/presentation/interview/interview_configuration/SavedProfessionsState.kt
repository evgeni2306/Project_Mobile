package com.jobinterviewapp.presentation.interview.interview_configuration

import com.jobinterviewapp.data.remote.dto.ProfessionDto

data class SavedProfessionsState(
    val savedProfessions: List<ProfessionDto> = emptyList(),
    val userKey: String? = null,
)