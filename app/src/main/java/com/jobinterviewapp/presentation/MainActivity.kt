package com.jobinterviewapp.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import com.jobinterviewapp.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.jobinterviewapp.presentation.interview.*
import com.jobinterviewapp.presentation.interview.interview_preview.InterviewPreviewScreen
import com.jobinterviewapp.presentation.knowledge_base.KnowledgeBaseScreen
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
                TransparentSystemBars()
                val navController = rememberNavController()
                val userSettings = dataStore.data.collectAsState(
                    initial = UserSettings(true)
                ).value
                Scaffold(
                    bottomBar = {
                        if(userSettings.authorized) {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        screen = Screen.KnowledgeBaseScreen,
                                        iconId = R.drawable.ic_knowledge_base,
                                    ),
                                    BottomNavItem(
                                        screen = Screen.FieldsOfActivityScreen,
                                        iconId = R.drawable.ic_interview,
                                    ),
                                ),
                                backgroundColor = MaterialTheme.colors.surface,
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
                    }
                ) {
                    Surface(
                        modifier = Modifier
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
fun TransparentSystemBars() {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(
            darkIcons = true,
            color = Color.Transparent,
            navigationBarContrastEnforced = false,
        )
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
        )
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier, userSettings: UserSettings) {
    NavHost(
        navController = navController,
        startDestination = if(userSettings.authorized) Screen.KnowledgeBaseScreen.route else Screen.RegistrationScreen.route,
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
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally() + fadeIn()
            ) {
                FieldsOfActivityScreen(navController)
            }
        }
        composable(
            route = "${Screen.DirectionsOfFieldScreen.route}/{${Constants.PARAM_FIELD_OF_ACTIVITY_ID}}",
            arguments = listOf(
                navArgument(Constants.PARAM_FIELD_OF_ACTIVITY_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            AnimatedVisibility(
                visible = true,
                enter = slideInHorizontally() + fadeIn()
            ) {
                DirectionsOfFieldScreen(navController)
            }
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
            route = "${Screen.ProfessionsOfTechnologyScreen.route}/{${Constants.PARAM_TECHNOLOGIES_OF_DIRECTION_ID}}",
            arguments = listOf(
                navArgument(Constants.PARAM_TECHNOLOGIES_OF_DIRECTION_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            ProfessionsOfTechnologyScreen(navController)
        }
        composable(
            route = "${Screen.InterviewPreviewScreen.route}/{${Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY}}",
            arguments = listOf(
                navArgument(Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY) {
                    type = NavType.IntType
                }
            )
        ) {
            InterviewPreviewScreen(navController)
        }
        composable(
            route = Screen.KnowledgeBaseScreen.route,
        ) {
            KnowledgeBaseScreen(navController)
        }
    }
}