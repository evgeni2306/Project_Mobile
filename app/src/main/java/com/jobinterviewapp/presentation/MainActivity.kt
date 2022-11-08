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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jobinterviewapp.presentation.authorization.RegistrationScreen
import com.jobinterviewapp.presentation.authorization.SignInScreen
import com.jobinterviewapp.core.presentation.ui.theme.JobInterviewAppTheme
import com.jobinterviewapp.presentation.components.BottomNavigationBar
import com.jobinterviewapp.presentation.home.FieldsOfActivityScreen
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
                                    screen = Screen.HomeScreen,
                                    iconId = R.drawable.ic_star,
                                ),
                                BottomNavItem(
                                    screen = Screen.HomeScreen,
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
            startDestination = if(authorized) Screen.HomeScreen.route else Screen.RegistrationScreen.route,
            modifier = modifier
        ) {
            composable(Screen.RegistrationScreen.route) {
                RegistrationScreen(navController)
            }
            composable(Screen.SignInScreen.route) {
                SignInScreen(navController)
            }
            composable(Screen.HomeScreen.route) {
                FieldsOfActivityScreen()
            }
        }
    }
}