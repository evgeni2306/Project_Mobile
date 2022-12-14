package com.jobinterviewapp.presentation.interview.interview_configuration.professions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.interview_configuration.components.FieldOfActivityScreenContent
import com.jobinterviewapp.presentation.interview.interview_configuration.professions.ProfessionsViewModel

@Composable
fun ProfessionsOfTechnologyScreen(
    navController: NavController,
    viewModel: ProfessionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        screen = Screen.ProfessionsOfTechnologyScreen,
        onItemClick = { navController.navigate(Screen.InterviewPreviewScreen.withArgs(it.id.toString())) },
        onRefreshClick = { viewModel.loadDirectionsOfField() },
        state = state,
    )
}