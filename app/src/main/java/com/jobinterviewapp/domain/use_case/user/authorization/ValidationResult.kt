package com.jobinterviewapp.domain.use_case.user.authorization

import com.weatherapp.core.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)