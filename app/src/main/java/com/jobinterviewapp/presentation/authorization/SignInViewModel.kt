package com.jobinterviewapp.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.user.SignInUserUseCase
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateAnyPassword
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateTextField
import com.jobinterviewapp.domain.use_case.user.authorization.ValidatePassword
import com.weatherapp.core.util.Resource
import com.weatherapp.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUserUseCase,
    private val validateTextField: ValidateTextField,
    private val validateAnyPassword: ValidateAnyPassword
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _authResults = MutableSharedFlow<UiText>()
    val authResults = _authResults.asSharedFlow()

    fun onEvent(event: AuthUiEvent) {
        when(event) {
            is AuthUiEvent.SignInLoginChanged -> {
                _state.update { it.copy(login = event.value) }
                val validationResult = validateTextField(event.value)
                _state.update { it.copy(loginError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignInPasswordChanged -> {
                _state.update { it.copy(password = event.value) }
                val validationResult = validateAnyPassword(event.value)
                _state.update { it.copy(passwordError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignIn -> {
                signInUser()
            }
            else -> {}
        }
    }

    private fun isValidForm(): Boolean {
        val stateValue = state.value

        return (stateValue.loginError == null
                && stateValue.passwordError == null)
    }

    private var signInJob: Job? = null

    fun signInUser() {
        signInJob?.cancel()
        signInJob = viewModelScope.launch {
            val stateValue = state.value
            _state.update { it.copy(isLoading = true) }

            signInUseCase(stateValue.login, stateValue.password).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                    }
                    is Resource.Error -> {
                        _authResults.emit(result.message)
                    }
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }
}