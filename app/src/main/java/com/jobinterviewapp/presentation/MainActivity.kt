package com.jobinterviewapp.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.jobinterviewapp.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jobinterviewapp.presentation.authorization.RegistrationScreen
import com.jobinterviewapp.presentation.authorization.SignInScreen
import com.jobinterviewapp.core.presentation.ui.theme.JobInterviewAppTheme
import com.jobinterviewapp.presentation.components.BottomNavigationBar
import com.jobinterviewapp.presentation.interview.*
import com.jobinterviewapp.presentation.interview.interview_preview.InterviewPreviewScreen
import com.jobinterviewapp.presentation.knowledge_base.KnowledgeBaseScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val Context.dataStore by dataStore("user-settings.json", UserSettingsSerializer)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JobInterviewAppTheme {
                // A surface container using the 'background' color from the theme
                TransparentSystemBars()
                val navController = rememberAnimatedNavController()
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController, modifier: Modifier, userSettings: UserSettings) {
    AnimatedNavHost(
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
        composable(
            route = Screen.FieldsOfActivityScreen.route,
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = Constants.ENTRY_NAVIGATION_ANIMATION_DURATION
                    ),
                    targetAlpha = 100f
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
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = Constants.ENTRY_NAVIGATION_ANIMATION_DURATION
                    ),
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
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
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
                fadeIn(
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION)
                )
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
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
            route = "${Screen.InterviewPreviewScreen.route}/{${Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY}}",
            arguments = listOf(
                navArgument(Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION)
                )
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(Constants.ENTRY_NAVIGATION_ANIMATION_DURATION),
                )
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
    }
}