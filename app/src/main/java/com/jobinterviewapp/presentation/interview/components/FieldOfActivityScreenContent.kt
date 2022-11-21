package com.jobinterviewapp.presentation.interview.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.jobinterviewapp.presentation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldOfActivityScreenContent(
    navController: NavController,
    fieldsOfActivity: List<FieldOfActivityDto>,
    screen: Screen,
    onItemClick: (FieldOfActivityDto) -> Unit,
) {
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 12.dp),
                )
                Spacer(modifier = Modifier.height(6.dp))
                FieldOfActivityList(
                    listState = fieldsOfActivity,
                    onItemClick = {
                        onItemClick(it)
                    }
                )
            }
        }
    }
}