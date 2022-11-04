package com.jobinterviewapp.presentation.authorization

import com.jobinterviewapp.R
import com.weatherapp.core.util.UiText

data class SignInState(
    val login: String = "",
    val password: String = "",
    val isValidForm: Boolean = false,
    val isLoading: Boolean = false,
    val loginError: UiText? = null,
    val passwordError: UiText? = null,
)