package com.jobinterviewapp.presentation.authorization

sealed interface AuthUiEvent {
    data class SignUpNameChanged(val value: String): AuthUiEvent
    data class SignUpSurnameChanged(val value: String): AuthUiEvent
    data class SignUpLoginChanged(val value: String): AuthUiEvent
    data class SignUpPasswordChanged(val value: String): AuthUiEvent
    data class SignInLoginChanged(val value: String): AuthUiEvent
    object SignUpNameClear: AuthUiEvent
    object SignUpSurnameClear: AuthUiEvent
    object SignUpPasswordClear: AuthUiEvent
    object SignUpLoginClear: AuthUiEvent
    object SignInLoginClear: AuthUiEvent
    object SignInPasswordClear: AuthUiEvent
    data class SignInPasswordChanged(val value: String): AuthUiEvent
    object SignIn: AuthUiEvent
    object SignUp: AuthUiEvent
}