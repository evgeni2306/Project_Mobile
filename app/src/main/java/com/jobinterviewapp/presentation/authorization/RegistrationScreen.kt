package com.jobinterviewapp.presentation.authorization

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.AuthTextField
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
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
        val keyboardMode = WindowInsets.isImeVisible
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp),
                verticalArrangement = Arrangement.Top,
            ) {
                val logoPadding = if(keyboardMode) 21.dp else 46.dp
                Text(
                    text = "ЛОГОТИП",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(vertical = logoPadding)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                )
                AuthTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userName,
                    onValueChange = { viewModel.onEvent(AuthUiEvent.SignUpNameChanged(it)) },
                    label = {
                        Text(
                            text = stringResource(R.string.name_field_hint),
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    helper = state.userNameError
                )
                AuthTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.userSurname,
                    onValueChange = {
                        viewModel.onEvent(AuthUiEvent.SignUpSurnameChanged(it))
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.surname_field_hint),
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    helper = state.userSurnameError
                )
                AuthTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.login,
                    onValueChange = {
                        viewModel.onEvent(AuthUiEvent.SignUpLoginChanged(it))
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.login_field_hint),
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    singleLine = true,
                    helper = state.loginError
                )
                AuthTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = stringResource(R.string.password_field_hint),
                        )
                    },
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = { viewModel.onEvent(AuthUiEvent.SignUp) },
                    enabled = state.isValidForm,
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = MaterialTheme.colors.primary,
                        disabledContentColor = MaterialTheme.colors.primaryVariant
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.register_button_text),
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            TextButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 40.dp, horizontal = 22.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { navController.navigate(Screen.SignInScreen.route) },
            ) {
                Row(
                ) {
                    Text(
                        text = stringResource(R.string.navigate_to_sign_in_hint),
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = stringResource(R.string.sign_in_button_text),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}