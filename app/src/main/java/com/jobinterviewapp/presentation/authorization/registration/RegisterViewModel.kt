package com.jobinterviewapp.presentation.authorization.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.models.Credential
import com.jobinterviewapp.domain.use_case.user.RegisterUserUseCase
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateTextField
import com.jobinterviewapp.domain.use_case.user.authorization.ValidatePassword
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateUserName
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateUserSurname
import com.jobinterviewapp.presentation.authorization.AuthUiEvent
import com.weatherapp.core.util.Resource
import com.weatherapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateUserName: ValidateUserName,
    private val validateSurname: ValidateUserSurname,
    private val validateLogin: ValidateTextField,
    private val validatePassword: ValidatePassword,
): ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _authError = MutableSharedFlow<UiText?>()
    val authError = _authError.asSharedFlow()

    fun onEvent(event: AuthUiEvent) {
        when(event) {
            is AuthUiEvent.SignUpNameClear -> {
                _state.update { it.copy(userName = "") }
                val validationResult = validateUserName("")
                _state.update { it.copy(userNameError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUpSurnameClear -> {
                _state.update { it.copy(userSurname = "") }
                val validationResult = validateSurname("")
                _state.update { it.copy(userSurnameError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUpLoginClear -> {
                _state.update { it.copy(login = "") }
                val validationResult = validateLogin("")
                _state.update { it.copy(loginError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUpPasswordClear -> {
                _state.update { it.copy(password = "") }
                val validationResult = validatePassword("")
                _state.update { it.copy(passwordError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUpNameChanged -> {
                _state.update { it.copy(userName = event.value) }
                val validationResult = validateUserName(event.value)
                _state.update { it.copy(userNameError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUpSurnameChanged -> {
                _state.update { it.copy(userSurname = event.value) }
                val validationResult = validateSurname(event.value)
                _state.update { it.copy(userSurnameError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUpLoginChanged -> {
                _state.update { it.copy(login = event.value) }
                val validationResult = validateLogin(event.value)
                _state.update { it.copy(loginError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUpPasswordChanged -> {
                _state.update { it.copy(password = event.value) }
                val validationResult = validatePassword(event.value)
                _state.update { it.copy(passwordError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignUp -> {
                signUp()
            }
            else -> {}
        }
    }

    private fun isValidForm(): Boolean {
        val stateValue = state.value

        return (stateValue.loginError == null
                && stateValue.passwordError == null
                && stateValue.userNameError == null
                && stateValue.userSurnameError == null
                && stateValue.login.isNotEmpty()
                && stateValue.password.isNotEmpty()
                && stateValue.userName.isNotEmpty()
                && stateValue.userSurname.isNotEmpty())
    }

    private fun signUp() {
        if(!isValidForm())
            return

        viewModelScope.launch {
            val stateValue = state.value
            _state.update { it.copy(isLoading = true) }

            val credential = Credential(
                stateValue.userName,
                stateValue.userSurname,
                stateValue.login,
                stateValue.password
            )
            registerUserUseCase(credential).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        _authError.emit(null)
                        _state.update { it.copy(userId = result.data) }
                    }
                    is Resource.Error -> {
                        _authError.emit(result.message)
                    }
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }
}