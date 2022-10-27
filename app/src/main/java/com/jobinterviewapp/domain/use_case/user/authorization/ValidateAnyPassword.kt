package com.jobinterviewapp.domain.use_case.user.authorization

import com.jobinterviewapp.R
import com.weatherapp.core.util.UiText
import javax.inject.Inject

class ValidateAnyPassword @Inject constructor() {
    operator fun invoke(login: String): ValidationResult {
        if(login.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_field_error)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}