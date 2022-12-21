package com.jobinterviewapp.presentation.interview.interview_configuration.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.ErrorTextHandler
import com.jobinterviewapp.presentation.interview.interview_configuration.InterviewConfigurationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldOfActivityScreenContent(
    navController: NavController,
    screen: Screen,
    onItemClick: (FieldOfActivityDto) -> Unit,
    state: InterviewConfigurationState,
    onRefreshClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            InterviewConfigurationTopBar(
                navController = navController,
                screen = screen,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ErrorTextHandler(
                error = state.error,
                onRefreshClick = onRefreshClick,
                modifier = Modifier.align(Alignment.Center),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FieldOfActivityList(
                    listState = state.fieldsOfActivity,
                    onItemClick = {
                        onItemClick(it)
                    }
                )
            }
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                    ,
                )
            }
        }
    }
}