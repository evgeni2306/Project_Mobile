package com.jobinterviewapp.presentation.authorization.sign_in

import com.jobinterviewapp.core.util.UiText

data class SignInState(
    val login: String = "",
    val password: String = "",
    val isValidForm: Boolean = false,
    val isLoading: Boolean = false,
    val loginError: UiText? = null,
    val passwordError: UiText? = null,
)