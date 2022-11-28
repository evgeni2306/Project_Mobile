package com.jobinterviewapp.presentation.authorization.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.user.SignInUserUseCase
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateAnyPassword
import com.jobinterviewapp.domain.use_case.user.authorization.ValidateTextField
import com.jobinterviewapp.presentation.authorization.AuthUiEvent
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.di.AppModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUserUseCase,
    private val validateTextField: ValidateTextField,
    private val validateAnyPassword: ValidateAnyPassword,
    private val dataStoreManager: AppModule.DataStoreManager,
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _authError = MutableSharedFlow<UiText?>()
    val authError = _authError.asSharedFlow()

    fun onEvent(event: AuthUiEvent) {
        when(event) {
            is AuthUiEvent.SignInLoginClear -> {
                _state.update { it.copy(login = "") }
                val validationResult = validateTextField("")
                _state.update { it.copy(loginError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
            is AuthUiEvent.SignInPasswordClear -> {
                _state.update { it.copy(password = "") }
                val validationResult = validateAnyPassword("")
                _state.update { it.copy(passwordError = validationResult.errorMessage) }
                _state.update { it.copy(isValidForm = isValidForm()) }
            }
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
                && stateValue.passwordError == null
                && stateValue.login.isNotEmpty()
                && stateValue.password.isNotEmpty())
    }

    private fun signInUser() {
        viewModelScope.launch {
            val stateValue = state.value
            _state.update { it.copy(isLoading = true) }

            signInUseCase(stateValue.login, stateValue.password).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        _authError.emit(null)
                        dataStoreManager.setAuthSettings(true, result.data)
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