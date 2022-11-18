package com.jobinterviewapp.presentation.authorization

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import com.jobinterviewapp.presentation.authorization.sign_in.SignInViewModel
import com.jobinterviewapp.presentation.authorization.components.AuthTextField
import com.jobinterviewapp.presentation.dataStore
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    BackHandler(
        enabled = true,
        onBack  = {
            navController.navigate(Screen.RegistrationScreen.route) {
                popUpTo(Screen.RegistrationScreen.route) {
                    inclusive = true
                }
            }
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.authError.collectLatest { authError ->
            if(authError == null) {
                context.dataStore.updateData {
                    it.copy(authorized = true)
                }
                navController.navigate(Screen.FieldsOfActivityScreen.route)
            }
            else {
                snackbarHostState.showSnackbar(
                    message = authError.asString(context)
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "ЛОГОТИП",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(vertical = 46.dp)
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.primary,
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
                        onClick = { viewModel.signInUser(); keyboardController?.hide() },
                        enabled = state.isValidForm,
                        colors = ButtonDefaults.buttonColors(
                            //disabledBackgroundColor = MaterialTheme.colorScheme.primary,
                            //disabledContentColor = MaterialTheme.colorScheme.primaryVariant
                        ),
                    ) {
                        if(state.isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.CenterVertically),
                                strokeWidth = 3.dp
                            )
                        }
                        else {
                            Text(
                                text = stringResource(R.string.sign_in_button_text),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
                TextButton(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        navController.navigate(Screen.RegistrationScreen.route) {
                            popUpTo(Screen.RegistrationScreen.route) {
                                inclusive = true
                            }
                        }
                    },
                ) {
                    Row(
                    ) {
                        Text(
                            text = stringResource(R.string.navigate_to_registration_hint),
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(Modifier.width(3.dp))
                        Text(
                            text = stringResource(R.string.register_button_text),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}