package com.jobinterviewapp.presentation.authorization

sealed class AuthUiEvent {
    data class SignUpNameChanged(val value: String): AuthUiEvent()
    data class SignUpSurnameChanged(val value: String): AuthUiEvent()
    data class SignUpLoginChanged(val value: String): AuthUiEvent()
    data class SignUpPasswordChanged(val value: String): AuthUiEvent()
    object SignUp: AuthUiEvent()
}