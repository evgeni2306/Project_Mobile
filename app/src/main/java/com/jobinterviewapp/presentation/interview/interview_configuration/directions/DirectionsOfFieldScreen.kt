package com.jobinterviewapp.presentation.interview.interview_configuration.directions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.interview_configuration.components.*
import com.jobinterviewapp.presentation.interview.interview_configuration.directions.DirectionsViewModel

@Composable
fun DirectionsOfFieldScreen(
    navController: NavController,
    viewModel: DirectionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        screen = Screen.DirectionsOfFieldScreen,
        onItemClick = { navController.navigate(Screen.TechnologiesOfDirectionScreen.withArgs(it.id.toString())) },
        onRefreshClick = { viewModel.loadDirectionsOfField() },
        state = state,
    )
}