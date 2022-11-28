package com.jobinterviewapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.jobinterviewapp.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jobinterviewapp.presentation.authorization.registration.RegistrationScreen
import com.jobinterviewapp.presentation.authorization.sign_in.SignInScreen
import com.jobinterviewapp.core.presentation.ui.theme.JobInterviewAppTheme
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.presentation.components.BottomNavigationBar
import com.jobinterviewapp.presentation.interview_configuration.*
import com.jobinterviewapp.presentation.interview_configuration.interview_preview.InterviewPreviewScreen
import com.jobinterviewapp.presentation.interview_simulation.InterviewSimulationScreen
import com.jobinterviewapp.presentation.knowledge_base.KnowledgeBaseScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val userSettings = runBlocking {
                AppModule.DataStoreManager(applicationContext).authSettings.first()
            }
            JobInterviewAppTheme {
                // A surface container using the 'background' color from the theme
                TransparentSystemBars()
                val navController = rememberAnimatedNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    screen = Screen.KnowledgeBaseScreen,
                                    iconOutlinedId = R.drawable.ic_knowledge_base_outlined,
                                    iconFilledId = R.drawable.ic_knowledge_base_filled,
                                ),
                                BottomNavItem(
                                    screen = Screen.FieldsOfActivityScreen,
                                    iconOutlinedId = R.drawable.ic_interview_outlined,
                                    iconFilledId = R.drawable.ic_interview_filled,
                                ),
                            ),
                            navController = navController,
                        )
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(
                                bottom = innerPadding.calculateBottomPadding(),
                            )
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
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
    val isDarkTheme = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(Color.Transparent)
        systemUiController.setNavigationBarColor(
            darkIcons = !isDarkTheme,
            color = Color.Transparent,
            navigationBarContrastEnforced = false,
        )
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = !isDarkTheme
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier, userSettings: AuthSettings) {
    AnimatedNavHost(
        navController = navController,
        startDestination = if(userSettings.authorized)
            Screen.KnowledgeBaseScreen.route
        else
            Screen.RegistrationScreen.route,
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
        composable(
            route = Screen.FieldsOfActivityScreen.route,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            }

        ) {
            FieldsOfActivityScreen(navController)
        }
        composable(
            route = "${Screen.DirectionsOfFieldScreen.route}/{${Constants.PARAM_FIELD_OF_ACTIVITY_ID}}",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = Constants.ENTRY_NAVIGATION_ANIMATION_DURATION
                    ),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
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
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = Constants.ENTRY_NAVIGATION_ANIMATION_DURATION
                    ),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
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
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = Constants.ENTRY_NAVIGATION_ANIMATION_DURATION
                    ),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            arguments = listOf(
                navArgument(Constants.PARAM_TECHNOLOGIES_OF_DIRECTION_ID) {
                    type = NavType.IntType
                }
            )
        ) {
            ProfessionsOfTechnologyScreen(navController)
        }
        composable(
            route = "${Screen.InterviewPreviewScreen.route}/{${Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY_ID}}",
            arguments = listOf(
                navArgument(Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY_ID) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = Constants.ENTRY_NAVIGATION_ANIMATION_DURATION
                    ),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
        ) {
            InterviewPreviewScreen(navController)
        }
        composable(
            route = Screen.KnowledgeBaseScreen.route,
        ) {
            KnowledgeBaseScreen(navController)
        }
        composable(
            route = Screen.InterviewSimulationScreen.route +
            "/{${Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY_ID}}" +
            "/{${Constants.PARAM_INTERVIEW_TASK_COUNT}}",
            arguments = listOf(
                navArgument(Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY_ID) { type = NavType.IntType },
                navArgument(Constants.PARAM_INTERVIEW_TASK_COUNT) { type = NavType.IntType }
            )
        ) {
            InterviewSimulationScreen(navController)
        }
    }
}