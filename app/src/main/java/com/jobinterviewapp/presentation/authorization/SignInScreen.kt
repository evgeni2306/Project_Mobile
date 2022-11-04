package com.jobinterviewapp.presentation.authorization

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.jobinterviewapp.presentation.components.AuthTextField
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "ЛОГОТИП",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier
                            .padding(vertical = 46.dp)
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    AuthTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.login,
                        onValueChange = {
                            viewModel.onEvent(AuthUiEvent.SignInLoginChanged(it))
                        },
                        label = {
                            Text(
                                stringResource(R.string.login_field_hint),
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        helper = state.loginError,
                        trailingIcon = {
                            if(state.login != "") {
                                IconButton(onClick = { viewModel.onEvent(AuthUiEvent.SignInLoginClear) }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear Icon",
                                        tint = MaterialTheme.colors.primary,
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
                                stringResource(R.string.password_field_hint),
                            )
                        },
                        value = state.password,
                        onValueChange = {
                            viewModel.onEvent(AuthUiEvent.SignInPasswordChanged(it))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true,
                        helper = state.passwordError,
                        visualTransformation = passwordVisualTransformation,
                        trailingIcon = {
                            Row() {
                                IconButton(
                                    onClick = {
                                        passwordVisualTransformation = if(passwordVisualTransformation != VisualTransformation.None)
                                            VisualTransformation.None
                                        else
                                            PasswordVisualTransformation()
                                    },
                                ) {
                                    Icon(
                                        painter = if(passwordVisualTransformation == VisualTransformation.None)
                                            painterResource(R.drawable.ic_visibility_24)
                                        else
                                            painterResource(R.drawable.ic_visibility_off_24),
                                        contentDescription = "Show password",
                                        tint = MaterialTheme.colors.primary,
                                    )
                                }
                            }
                        },
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        onClick = { viewModel.signInUser() },
                        enabled = state.isValidForm,
                        colors = ButtonDefaults.buttonColors(
                            disabledBackgroundColor = MaterialTheme.colors.primary,
                            disabledContentColor = MaterialTheme.colors.primaryVariant
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.sign_in_button_text),
                            style = MaterialTheme.typography.body1,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                TextButton(
                    modifier = Modifier
                        .padding(vertical = 40.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = { navController.navigate(Screen.RegistrationScreen.route) },
                ) {
                    Row(
                    ) {
                        Text(
                            text = stringResource(R.string.navigate_to_registration_hint),
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.body1,
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = stringResource(R.string.register_button_text),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
        }
    }
}