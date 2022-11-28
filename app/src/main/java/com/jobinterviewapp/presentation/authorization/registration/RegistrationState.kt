package com.jobinterviewapp.presentation.authorization.registration

import com.jobinterviewapp.core.util.UiText

data class RegistrationState(
    val isLoading: Boolean = false,
    val isValidForm: Boolean = false,
    val userName: String = "",
    val userNameError: UiText? = null,
    val userSurname: String = "",
    val userSurnameError: UiText? = null,
    val login: String = "",
    val loginError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
)