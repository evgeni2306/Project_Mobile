package com.jobinterviewapp.presentation.interview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.interview.components.FieldOfActivityScreenContent

@Composable
fun FieldsOfActivityScreen(
    navController: NavController,
    viewModel: FieldsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    FieldOfActivityScreenContent(
        navController = navController,
        fieldsOfActivity = state.fieldsOfActivity,
        screenTitle = stringResource(id = R.string.field_of_activity_screen_placeholder),
        screen = Screen.FieldsOfActivityScreen,
        onItemClick = { navController.navigate(Screen.DirectionsOfFieldScreen.withArgs(it.id.toString())) }
    )
}