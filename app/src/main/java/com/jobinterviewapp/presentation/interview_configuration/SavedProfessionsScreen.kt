package com.jobinterviewapp.presentation.interview_configuration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview_configuration.components.InterviewConfigurationTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedProfessionsScreen(
    navController: NavController,
    viewModel: SavedProfessionsViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            InterviewConfigurationTopBar(
                navController = navController,
                screen = Screen.SavedProfessionsScreen,
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                ,
        ) {
            val state = viewModel.state.collectAsState().value
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.savedProfessions) { profession ->
                    ListItem(
                        headlineText = {
                            profession.name
                        },
                        trailingContent = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                            )
                        }
                    )
                }
            }

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        25.dp
                    )
                ,
                onClick = {
                    navController.navigate(Screen.FieldsOfActivityScreen.route)
                }
            ) {
                Text(
                    text = stringResource(R.string.create_new_interview_button_text),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}