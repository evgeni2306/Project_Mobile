package com.jobinterviewapp.presentation.interview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.components.FieldOfActivityList
import com.jobinterviewapp.presentation.interview.components.ScreenPlaceholder
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.interview.components.InterviewConfigurationTopBar

@Composable
fun TechnologiesOfDirectionScreen(
    navController: NavController,
    viewModel: TechnologiesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            InterviewConfigurationTopBar(
                navController = navController,
                screen = Screen.ProfessionsOfTechnologyScreen,
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
                ScreenPlaceholder(text = stringResource(R.string.technology_of_direction_screen_placeholder))
                FieldOfActivityList(listState = state.fieldsOfActivity, onItemClick = {
                    navController.navigate(Screen.ProfessionsOfTechnologyScreen.withArgs(it.id.toString()))
                })
            }
        }
    }
}