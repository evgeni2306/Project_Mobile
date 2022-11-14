package com.jobinterviewapp.domain.use_case.user.authorization

import com.jobinterviewapp.R
import com.jobinterviewapp.core.util.UiText
import javax.inject.Inject

class ValidateTextField @Inject constructor() {
    operator fun invoke(login: String): ValidationResult {
        if(login.isBlank()) {
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