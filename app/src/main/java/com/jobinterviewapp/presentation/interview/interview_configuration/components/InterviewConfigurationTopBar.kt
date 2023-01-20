package com.jobinterviewapp.presentation.interview.interview_configuration.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.DefaultTopBar
import com.jobinterviewapp.presentation.components.TopBarTitleText

@Composable
fun InterviewConfigurationTopBar(
    navController: NavController,
    screen: Screen,
) {
    DefaultTopBar(
        title = {
            TopBarTitleText(screen.screenName.asString())
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = null,
                )
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