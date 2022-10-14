package com.jobinterviewapp.domain.use_case.user.authorization

import com.jobinterviewapp.R
import com.weatherapp.core.util.UiText
import javax.inject.Inject

class ValidateUserSurname @Inject constructor() {
    operator fun invoke(surname: String): ValidationResult {
        if(surname.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_field_error)
            )
        }
        if(!surname.matches("^[A-ZА-Я][a-zа-я][a-zа-я]*".toRegex())) {
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