package com.jobinterviewapp.presentation.interview_configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview_configuration.components.FieldOfActivityScreenContent

@Composable
fun TechnologiesOfDirectionScreen(
    navController: NavController,
    viewModel: TechnologiesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        fieldsOfActivity = state.fieldsOfActivity,
        screen = Screen.TechnologiesOfDirectionScreen,
        onItemClick = { navController.navigate(Screen.ProfessionsOfTechnologyScreen.withArgs(it.id.toString())) },
        onRefreshClick = { viewModel.loadDirectionsOfField() },
        error = state.error,
    )
}