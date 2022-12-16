package com.jobinterviewapp.presentation.interview.interview_configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.interview_configuration.components.FieldOfActivityScreenContent

@Composable
fun FieldsOfActivityScreen(
    navController: NavController,
    viewModel: FieldsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        fieldsOfActivity = state.fieldsOfActivity,
        screen = Screen.FieldsOfActivityScreen,
        onItemClick = { navController.navigate(Screen.DirectionsOfFieldScreen.withArgs(it.id.toString())) },
        onRefreshClick = { viewModel.loadFieldsOfActivity() },
        error = state.error,
    )
}