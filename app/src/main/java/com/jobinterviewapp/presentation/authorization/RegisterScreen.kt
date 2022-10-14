package com.jobinterviewapp.presentation.authorization

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.components.CustomTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.authResults.collectLatest { authResult ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = authResult.asString(context)
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.userName,
                onValueChange = { viewModel.onEvent(AuthUiEvent.SignUpNameChanged(it)) },
                label = { Text(text = stringResource(R.string.name_field_hint)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                helper = state.userNameError
            )
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.userSurname,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignUpSurnameChanged(it))
                },
                label = { Text(text = stringResource(R.string.surname_field_hint)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                helper = state.userSurnameError
            )
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.login,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignUpLoginChanged(it))
                },
                label = { Text(stringResource(R.string.login_field_hint)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                helper = state.loginError
            )
            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.password_field_hint)) },
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignUpPasswordChanged(it))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                helper = state.passwordError,
                visualTransformation = PasswordVisualTransformation(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(AuthUiEvent.SignUp) },
                enabled = state.isValidForm,
            ) {
                Text(text = stringResource(R.string.register_button_text))
            }
        }
    }
}