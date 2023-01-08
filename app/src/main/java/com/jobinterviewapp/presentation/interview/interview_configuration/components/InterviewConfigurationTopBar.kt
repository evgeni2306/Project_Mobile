package com.jobinterviewapp.presentation.interview.interview_configuration.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.DefaultTopBar
import com.jobinterviewapp.presentation.components.TopBarTitleText
import kotlinx.coroutines.launch

@Composable
fun InterviewConfigurationTopBar(
    navController: NavController,
    screen: Screen,
) {
    val context = LocalContext.current
    val localLifecycleOwner = LocalLifecycleOwner.current
    DefaultTopBar(
        title = {
            TopBarTitleText(screen.screenName.asString())
        },
        onProfileClick = {
            localLifecycleOwner.lifecycleScope.launch {
                AppModule.DataStoreManager(context).setAuthStatus(
                    authorized = false
                )
            }
            navController.navigate(Screen.RegistrationScreen.route) {
                popUpTo(Screen.KnowledgeBaseScreen.route) {
                    inclusive = true
                }
            }
        },
        navigationIcon = {
            if(screen.route != Screen.SavedProfessionsScreen.route) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        }
    )
}