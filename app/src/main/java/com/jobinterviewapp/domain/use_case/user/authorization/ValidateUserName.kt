package com.jobinterviewapp.domain.use_case.user.authorization

import com.jobinterviewapp.R
import com.jobinterviewapp.core.util.UiText
import javax.inject.Inject

class ValidateUserName @Inject constructor() {
    operator fun invoke(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_field_error)
            )
        }
        if(!name.matches("^[A-ZА-Я][a-zа-я][a-zа-я]*".toRegex())) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.invalid_format_error)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}