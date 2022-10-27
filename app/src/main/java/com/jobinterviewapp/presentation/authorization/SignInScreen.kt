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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.CustomTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
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
                value = state.login,
                onValueChange = {
                    viewModel.onEvent(AuthUiEvent.SignInLoginChanged(it))
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
                    viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
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
                onClick = { viewModel.signInUser() },
                enabled = state.isValidForm,
            ) {
                Text(text = stringResource(R.string.sign_in_button_text))
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(Screen.RegistrationScreen.route) },
            ) {
                Row(
                ) {
                    Text(
                        text = stringResource(R.string.navigate_to_registration_hint),
                        color = MaterialTheme.colors.onBackground,
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.register_button_text),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}