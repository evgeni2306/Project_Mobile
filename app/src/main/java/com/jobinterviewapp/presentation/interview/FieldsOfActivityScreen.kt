package com.jobinterviewapp.presentation.interview

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.components.FieldOfActivityList
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.interview.components.InterviewConfigurationTopBar
import com.jobinterviewapp.presentation.interview.components.ScreenPlaceholder

@Composable
fun FieldsOfActivityScreen(
    navController: NavController,
    viewModel: FieldsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            InterviewConfigurationTopBar(
                navController = navController,
                screen = Screen.FieldsOfActivityScreen,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScreenPlaceholder(stringResource(id = R.string.field_of_activity_screen_placeholder))
                FieldOfActivityList(
                    listState = state.fieldsOfActivity,
                    onItemClick = {
                        navController.navigate(Screen.DirectionsOfFieldScreen.withArgs(it.id.toString()))
                    }
                )
            }
        }
    }
}