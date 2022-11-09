package com.jobinterviewapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.home.components.FieldOfActivityList
import com.jobinterviewapp.presentation.home.components.ScreenPlaceholder
import com.jobinterviewapp.R

@Composable
fun DirectionsOfFieldScreen(
    navController: NavController,
    viewModel: DirectionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenPlaceholder(text = stringResource(id = R.string.direction_of_field_screen_placeholder))
            FieldOfActivityList(
                listState = state.fieldsOfActivity,
                onItemClick = {
                    navController.navigate(Screen.TechnologiesOfDirectionScreen.withArgs(it.id.toString()))
                }
            )
        }
    }
}