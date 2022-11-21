package com.jobinterviewapp.presentation.interview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.components.FieldOfActivityScreenContent

@Composable
fun ProfessionsOfTechnologyScreen(
    navController: NavController,
    viewModel: ProfessionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        fieldsOfActivity = state.fieldsOfActivity,
        screen = Screen.ProfessionsOfTechnologyScreen,
        onItemClick = { navController.navigate(Screen.InterviewPreviewScreen.withArgs(it.id.toString())) }
    )
}