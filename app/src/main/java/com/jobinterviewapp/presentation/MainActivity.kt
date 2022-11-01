package com.jobinterviewapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jobinterviewapp.presentation.authorization.RegisterScreen
import com.jobinterviewapp.presentation.authorization.SignInScreen
import com.jobinterviewapp.core.presentation.ui.theme.JobInterviewAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JobInterviewAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .systemBarsPadding()
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
                    Navigation(navController, Modifier)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.RegistrationScreen.route,
        modifier = modifier
    ) {
        composable(Screen.RegistrationScreen.route) {
            RegisterScreen(navController)
        }
        composable(Screen.SignInScreen.route) {
            SignInScreen(navController)
        }
    }
}