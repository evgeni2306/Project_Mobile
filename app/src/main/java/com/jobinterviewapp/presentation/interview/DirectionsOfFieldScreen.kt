package com.jobinterviewapp.presentation.interview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.components.*

@Composable
fun DirectionsOfFieldScreen(
    navController: NavController,
    viewModel: DirectionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        fieldsOfActivity = state.fieldsOfActivity,
        screen = Screen.DirectionsOfFieldScreen,
        onItemClick = { navController.navigate(Screen.TechnologiesOfDirectionScreen.withArgs(it.id.toString())) }
    )
}