package com.jobinterviewapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobinterviewapp.presentation.home.components.FieldOfActivityList

@Composable
fun FieldsOfActivityScreen(
    viewModel: FieldsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FieldOfActivityList(state.fieldsOfActivity)
    }
}