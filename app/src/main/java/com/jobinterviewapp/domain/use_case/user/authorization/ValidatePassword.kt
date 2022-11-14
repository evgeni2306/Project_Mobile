package com.jobinterviewapp.domain.use_case.user.authorization

import com.jobinterviewapp.R
import com.jobinterviewapp.core.util.UiText
import javax.inject.Inject

class ValidatePassword @Inject constructor() {
    operator fun invoke(password: String): ValidationResult {
        if(password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.empty_field_error)
            )
        }
        else if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.short_password_error)
            )
        }
        else if(!password.matches(".*[A-ZА-Я].*".toRegex())) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_capital_letters_error)
            )
        }
        else if(!password.matches(".*[a-zа-я].*".toRegex())) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_lower_letters_error)
            )
        }
        else if(!password.matches(".*[1-9].*".toRegex())) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(R.string.password_digits_error)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}