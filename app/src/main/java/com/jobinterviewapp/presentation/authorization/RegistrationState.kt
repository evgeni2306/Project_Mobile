package com.jobinterviewapp.presentation.authorization

import com.jobinterviewapp.R
import com.weatherapp.core.util.UiText

data class RegistrationState(
    val isLoading: Boolean = false,
    val isValidForm: Boolean = false,
    val userId: String = "",
    val userName: String = "",
    val userNameError: UiText? = UiText.StringResource(R.string.field_required_error),
    val userSurname: String = "",
    val userSurnameError: UiText? = UiText.StringResource(R.string.field_required_error),
    val login: String = "",
    val loginError: UiText? = UiText.StringResource(R.string.field_required_error),
    val password: String = "",
    val passwordError: UiText? = UiText.StringResource(R.string.field_required_error),
)