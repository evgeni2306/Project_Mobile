package com.jobinterviewapp.presentation.interview.interview_configuration.technologies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.interview_configuration.components.FieldOfActivityScreenContent
import com.jobinterviewapp.presentation.interview.interview_configuration.technologies.TechnologiesViewModel

@Composable
fun TechnologiesOfDirectionScreen(
    navController: NavController,
    viewModel: TechnologiesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        screen = Screen.TechnologiesOfDirectionScreen,
        onItemClick = { navController.navigate(Screen.ProfessionsOfTechnologyScreen.withArgs(it.id.toString())) },
        onRefreshClick = { viewModel.loadDirectionsOfField() },
        state = state,
    )
}