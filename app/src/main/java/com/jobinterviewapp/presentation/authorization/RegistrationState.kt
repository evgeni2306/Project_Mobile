package com.jobinterviewapp.presentation.authorization

import com.weatherapp.core.util.UiText

data class RegistrationState(
    val isLoading: Boolean = false,
    val registerError: UiText? = null,
    val userId: Int? = null,
    val userName: String = "",
    val userNameError: UiText? = null,
    val userSurname: String = "",
    val userSurnameError: UiText? = null,
    val login: String = "",
    val loginError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null,
)