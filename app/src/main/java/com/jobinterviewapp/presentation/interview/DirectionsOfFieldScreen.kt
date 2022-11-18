package com.jobinterviewapp.presentation.interview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.R
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
        screenTitle = stringResource(id = R.string.direction_of_field_screen_placeholder),
        screen = Screen.DirectionsOfFieldScreen,
        onItemClick = { navController.navigate(Screen.TechnologiesOfDirectionScreen.withArgs(it.id.toString())) }
    )
}