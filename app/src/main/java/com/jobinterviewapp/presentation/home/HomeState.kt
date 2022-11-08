package com.jobinterviewapp.presentation.home

import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.weatherapp.core.util.UiText

data class HomeState(
    val fieldsOfActivity: List<FieldOfActivityDto> = emptyList(),
    val isLoading: Boolean = false,
)