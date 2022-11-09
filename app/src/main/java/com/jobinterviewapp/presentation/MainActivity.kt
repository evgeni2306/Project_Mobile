package com.jobinterviewapp.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.jobinterviewapp.R
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.datastore.dataStore
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jobinterviewapp.presentation.authorization.RegistrationScreen
import com.jobinterviewapp.presentation.authorization.SignInScreen
import com.jobinterviewapp.core.presentation.ui.theme.JobInterviewAppTheme
import com.jobinterviewapp.presentation.components.BottomNavigationBar
import com.jobinterviewapp.presentation.home.DirectionsOfFieldScreen
import com.jobinterviewapp.presentation.home.FieldsOfActivityScreen
import com.jobinterviewapp.presentation.home.ProfessionsOfTechnologyScreen
import com.jobinterviewapp.presentation.home.TechnologiesOfDirectionScreen
import dagger.hilt.android.AndroidEntryPoint

val Context.dataStore by dataStore("user-settings.json", UserSettingsSerializer)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JobInterviewAppTheme {
                // A surface container using the 'background' color from the theme
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
                val navController = rememberNavController()
                val userSettings = dataStore.data.collectAsState(
                    initial = UserSettings()
                ).value
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    screen = Screen.FieldsOfActivityScreen,
                                    iconId = R.drawable.ic_star,
                                ),
                                BottomNavItem(
                                    screen = Screen.FieldsOfActivityScreen,
                                    iconId = R.drawable.ic_star,
                                ),
                            ),
                            backgroundColor = MaterialTheme.colors.background,
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.screen.route) {
                                    popUpTo(it.screen.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .systemBarsPadding()
                            .fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Navigation(navController, Modifier, userSettings)
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier, userSettings: UserSettings) {
    userSettings.authorized?.let { authorized ->
        NavHost(
            navController = navController,
            startDestination = if(authorized) Screen.FieldsOfActivityScreen.route else Screen.RegistrationScreen.route,
            modifier = modifier
        ) {
            composable(
                route = Screen.RegistrationScreen.route,
            ) {
                RegistrationScreen(navController)
            }
            composable(Screen.SignInScreen.route) {
                SignInScreen(navController)
            }
            composable(Screen.FieldsOfActivityScreen.route) {
                FieldsOfActivityScreen(navController)
            }
            composable(
                route = "${Screen.DirectionsOfFieldScreen.route}/{${Constants.PARAM_FIELD_OF_ACTIVITY_ID}}",
                arguments = listOf(
                    navArgument(Constants.PARAM_FIELD_OF_ACTIVITY_ID) {
                        type = NavType.IntType
                    }
                )
            ) {
                DirectionsOfFieldScreen(navController)
            }
            composable(
                route = "${Screen.TechnologiesOfDirectionScreen.route}/{${Constants.PARAM_DIRECTION_OF_FIELD_ID}}",
                arguments = listOf(
                    navArgument(Constants.PARAM_DIRECTION_OF_FIELD_ID) {
                        type = NavType.IntType
                    }
                )
            ) {
                TechnologiesOfDirectionScreen(navController)
            }
            composable(
                route = Screen.ProfessionsOfTechnologyScreen.route,
                arguments = listOf(
                    navArgument(Constants.PARAM_TECHNOLOGIES_OF_DIRECTION_ID) {
                        type = NavType.IntType
                    }
                )
            ) {
                ProfessionsOfTechnologyScreen(navController)
            }
        }
    }
}