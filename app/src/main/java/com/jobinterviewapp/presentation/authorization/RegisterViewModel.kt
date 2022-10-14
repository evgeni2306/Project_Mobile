package com.jobinterviewapp.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.models.Credential
import com.jobinterviewapp.domain.use_case.user.RegisterUserUseCase
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateLogin
import com.jobinterviewapp.domain.use_case.user.authorization.ValidatePassword
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateUserName
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateUserSurname
import com.weatherapp.core.util.Resource
import com.weatherapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateUserName: ValidateUserName,
    private val validateSurname: ValidateUserSurname,
    private val validateLogin: ValidateLogin,
    private val validatePassword: ValidatePassword,
): ViewModel() {
    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _authResults = MutableSharedFlow<UiText>()
    val authResults = _authResults.asSharedFlow()

    fun onEvent(event: AuthUiEvent) {
        when(event) {
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
        }
    }

    private fun isValidForm(): Boolean {
        val stateValue = state.value

        return (stateValue.loginError == null
                && stateValue.passwordError == null
                && stateValue.userNameError == null
                && stateValue.userSurnameError == null)
    }

    private var signUpJob: Job? = null

    private fun signUp() {
        if(!isValidForm())
            return

        signUpJob?.cancel()
        signUpJob = viewModelScope.launch {
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
                        _state.update { it.copy(userId = result.data) }
                    }
                    is Resource.Error -> {
                        _authResults.emit(result.message)
                    }
                }

                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}