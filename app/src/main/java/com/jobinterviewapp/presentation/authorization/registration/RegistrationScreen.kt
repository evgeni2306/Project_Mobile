package com.jobinterviewapp.presentation.authorization.registration

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.authorization.AuthUiEvent
import com.jobinterviewapp.presentation.authorization.components.AuthTextField
import com.jobinterviewapp.presentation.components.FullLogo
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.authError.collectLatest { authError ->
            if(authError == null) {
                navController.navigate(Screen.KnowledgeBaseScreen.route) {
                    popUpTo(
                        route = Screen.RegistrationScreen.route,
                    ) {
                        inclusive = true
                    }
                }
            }
            else {
                snackbarHostState.showSnackbar(
                    message = authError.asString(context)
                )
            }
        }
    }

    (context as? Activity)?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        val keyboardMode = WindowInsets.isImeVisible
        val keyboardController = LocalSoftwareKeyboardController.current
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize(),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val logoPadding = if(keyboardMode) 10.dp else 50.dp
                    FullLogo(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        logoPadding
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
                        helper = state.userNameError,
                        trailingIcon = {
                            if(state.userName != "") {
                                IconButton(onClick = { viewModel.onEvent(AuthUiEvent.SignUpNameClear) }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                    )
                                }
                            }
                        },
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
                        trailingIcon = {
                            if(state.userSurname != "") {
                                IconButton(onClick = { viewModel.onEvent(AuthUiEvent.SignUpSurnameClear) }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        helper = state.userSurnameError,
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
                        helper = state.loginError,
                        trailingIcon = {
                            if(state.login != "") {
                                IconButton(onClick = { viewModel.onEvent(AuthUiEvent.SignUpLoginClear) }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = null,
                                    )
                                }
                            }
                        },
                    )

                    var passwordVisualTransformation: VisualTransformation
                        by remember { mutableStateOf(PasswordVisualTransformation()) }
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
                        visualTransformation = passwordVisualTransformation,
                        trailingIcon = {
                            Row() {
                                IconButton(onClick = {
                                    passwordVisualTransformation = if(passwordVisualTransformation != VisualTransformation.None)
                                        VisualTransformation.None
                                    else
                                        PasswordVisualTransformation()
                                }) {
                                    Icon(
                                        painter = if(passwordVisualTransformation == VisualTransformation.None)
                                            painterResource(R.drawable.ic_visibility_24)
                                        else
                                            painterResource(R.drawable.ic_visibility_off_24),
                                        contentDescription = null,
                                    )
                                }
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            ,
                        onClick = {
                            viewModel.onEvent(AuthUiEvent.SignUp)
                            keyboardController?.hide()
                        },
                        enabled = state.isValidForm,
                    ) {
                        if(state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.CenterVertically),
                                strokeWidth = 3.dp
                            )
                        }
                        else {
                            Text(
                                text = stringResource(R.string.register_button_text),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
                Column() {
                    TextButton(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 30.dp)
                            .fillMaxWidth(),
                        onClick = { navController.navigate(Screen.SignInScreen.route) },
                    ) {
                        Text(
                            text = stringResource(R.string.navigate_to_sign_in_hint),
                        )
                    }
                }
            }
        }
    }
}

