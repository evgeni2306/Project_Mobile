package com.jobinterviewapp.presentation.interview.interview_result

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.ErrorTextHandler
import com.jobinterviewapp.presentation.components.TaskItemCard
import com.jobinterviewapp.presentation.interview.components.TaskCategoryElement

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InterviewResultScreen(
    navController: NavController,
    viewModel: InterviewResultViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        bottomBar = {
            if(!state.isLoading && state.error == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.interview_result_done_button_text),
                        )
                    }

                    TextButton(
                        onClick = {
                            navController.navigate(
                                route = Screen.InterviewPreviewScreen.withArgs(
                                    state.professionId.toString()
                                )
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.try_interview_again_button_text)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 100.dp,
                )
            ,
        ) {
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            ErrorTextHandler(
                error = state.error,
                modifier = Modifier.align(Alignment.Center),
                onRefreshClick = viewModel::loadInterviewResult,
            )
            if(!state.isLoading && state.error == null && state.rightAnswersPercentage != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                    ,
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    ElevatedCard() {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 0.dp
                                )
                            ,
                        ) {
                            ListItem(
                                modifier = Modifier.fillMaxWidth(),
                                trailingContent = {
                                    Text(
                                        text = "${state.rightAnswersCount}/${state.answersCount}",
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                },
                                headlineText = {
                                    Text(
                                        text = stringResource(R.string.right_answers_text),
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                },
                            )
                            LinearProgressIndicator(
                                progress = state.rightAnswersPercentage,
                                modifier = Modifier
                                    .padding(vertical = 0.dp, horizontal = 18.dp)
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }

                    Text(
                        text = stringResource(R.string.wrong_answers_title),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                    )
                    CompositionLocalProvider(
                        LocalOverscrollConfiguration provides OverscrollConfiguration(
                            glowColor = MaterialTheme.colorScheme.background,
                        )
                    ) {
                        LazyColumn(
                        ) {
                            items(count = state.wrongAnswers.size) { index ->
                                TaskItemCard(
                                    task = state.wrongAnswers[index],
                                    onFavoriteTaskClicked = { (viewModel::onFavoriteTaskClicked)(index) }
                                )
                            }
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}