package com.jobinterviewapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.home.components.FieldOfActivityList

@Composable
fun ProfessionsOfTechnologyScreen(
    navController: NavController,
    viewModel: ProfessionsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FieldOfActivityList(
            listState = state.fieldsOfActivity,
            onItemClick = {
            }
        )
    }
}