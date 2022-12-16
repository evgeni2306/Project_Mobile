package com.jobinterviewapp.presentation.interview.interview_result

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.components.TaskCategoryElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewResultScreen(
    navController: NavController,
    viewModel: InterviewResultViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    if(state.rightAnswersPercentage == null) {
        return
    }
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    ,
            ) {
                ElevatedCard() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
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
                            }
                        )
                        LinearProgressIndicator(
                            progress = state.rightAnswersPercentage,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .height(10.dp)
                                .fillMaxWidth()
                                ,
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.wrong_answers_title),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                )
                LazyColumn(
                ) {
                    items(state.wrongAnswers) { task ->
                        ElevatedCard(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                ,
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    TaskCategoryElement(
                                        categoryName = task.category,
                                        modifier = Modifier,
                                    )
                                    IconButton(
                                        onClick = { /*TODO*/ },
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_favorites),
                                            contentDescription = null
                                        )
                                    }
                                }
                                Text(
                                    style = MaterialTheme.typography.titleMedium,
                                    text = task.question,
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        navController.navigate(
                            route = Screen.InterviewSimulationScreen.withArgs(
                                state.professionId.toString()
                            )
                        )
                    }
                ) {
                    Text(
                        text = stringResource(R.string.try_interview_again_button_text)
                    )
                }
            }
        }
    }
}