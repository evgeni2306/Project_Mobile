package com.jobinterviewapp.presentation.interview.interview_configuration.saved_professions

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.ErrorTextHandler
import com.jobinterviewapp.presentation.interview.interview_configuration.components.InterviewConfigurationTopBar
import com.jobinterviewapp.presentation.interview.interview_configuration.saved_professions.SavedProfessionsViewModel

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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                ,
        ) {
            val state = viewModel.state.collectAsState().value
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
            ErrorTextHandler(
                error = state.error,
                modifier = Modifier.align(Alignment.Center),
                onRefreshClick = viewModel::getSavedProfessions
            )
            if(!state.isLoading && state.error == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.savedProfessions) { profession ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        route = Screen.InterviewPreviewScreen.withArgs(
                                            profession.professionId.toString()
                                        )
                                    )
                                },
                            headlineText = {
                                Text(
                                    text = profession.name,
                                    style = MaterialTheme.typography.titleMedium,
                                )
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

                if(state.savedProfessions.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(30.dp)
                        ,
                        text = stringResource(
                            id = R.string.saved_professions_placeholder,
                            stringResource(R.string.create_new_interview_button_text)
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
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
}