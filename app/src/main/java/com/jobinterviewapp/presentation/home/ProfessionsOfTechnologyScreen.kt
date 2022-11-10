package com.jobinterviewapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.home.components.FieldOfActivityList
import com.jobinterviewapp.presentation.home.components.InterviewConfigurationTopBar
import com.jobinterviewapp.presentation.home.components.ScreenPlaceholder

@Composable
fun ProfessionsOfTechnologyScreen(
    navController: NavController,
    viewModel: ProfessionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            InterviewConfigurationTopBar(
                navController = navController,
                screen = Screen.ProfessionsOfTechnologyScreen,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenPlaceholder(text = stringResource(id = R.string.profession_of_technology_screen_placeholder))
                FieldOfActivityList(
                    listState = state.fieldsOfActivity,
                    onItemClick = {
                    }
                )
            }
        }
    }
}