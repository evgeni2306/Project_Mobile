package com.jobinterviewapp.presentation.interview.interview_simulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun InterviewSimulationScreen(
    navController: NavController,
    viewModel: InterviewSimulationViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.interview_simulation_subtitle),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },

                actions = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = null,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box {
            state.currentTask?.let { currentTask ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(90.dp),
                        painter = painterResource(id = R.drawable.ic_employer),
                        contentDescription = null,
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.
                                padding(horizontal = 16.dp)
                    ) {
                        ElevatedCard(
                            modifier = Modifier
                                .size(25.dp),
                            shape = TriangleShape(),
                        ) {}
                        var expanded by remember { mutableStateOf(false) }
                        ElevatedCard(
                            modifier = Modifier
                                .animateContentSize()
                        ) {
                            val isVisible = remember {
                                MutableTransitionState(false).apply {
                                    // Start the animation immediately.
                                    this.targetState = true
                                }
                            }
                            AnimatedVisibility(
                                visibleState = isVisible,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    if(state.isEndOfInterview) {
                                        Text(
                                            text = stringResource(R.string.end_of_interview_text),
                                            style = MaterialTheme.typography.titleMedium,
                                        )
                                        Spacer(Modifier.height(10.dp))
                                        Button(
                                            onClick = {
                                                navController.navigate(
                                                    route = Screen.InterviewResultScreen.withArgs(
                                                        state.interviewId.toString(),
                                                        state.professionId.toString(),
                                                    )
                                                )
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(R.string.interview_result_button_text),
                                            )
                                        }
                                    }
                                    else {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            TaskCategoryElement(
                                                categoryName = currentTask.category,
                                                modifier = Modifier,
                                            )
                                            IconButton(
                                                onClick = {
                                                    viewModel.onFavoriteTaskClicked()
                                                }
                                            ) {
                                                Icon(
                                                    painter = if(currentTask.isFavorite)
                                                        painterResource(id = R.drawable.ic_favorite_filled)
                                                    else
                                                        painterResource(id = R.drawable.ic_favorite_border),
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                        Text(
                                            style = MaterialTheme.typography.titleMedium,
                                            text = currentTask.question,
                                            modifier = Modifier
                                                .padding(horizontal = 10.dp),
                                            textAlign = TextAlign.Center,
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        TextButton(
                                            modifier = Modifier
                                                .width(150.dp),
                                            onClick = {
                                                expanded = !expanded
                                            }
                                        ) {
                                            Text(
                                                text = stringResource(
                                                    if(!expanded)
                                                        R.string.compare_answer_button_text
                                                    else
                                                        R.string.hide_answer_button_text
                                                )
                                            )
                                        }
                                        AnimatedVisibility(
                                            visible = expanded,
                                        ) {
                                            Card {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(20.dp)
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.right_answer_title),
                                                        style = MaterialTheme.typography.titleMedium,
                                                    )
                                                    Text(
                                                        modifier = Modifier
                                                            .padding(10.dp),
                                                        text = currentTask.answer,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        textAlign = TextAlign.Center,
                                                    )
                                                    Spacer(modifier = Modifier.height(10.dp))
                                                    Text(
                                                        text = stringResource(R.string.is_right_answer_text),
                                                        style = MaterialTheme.typography.titleSmall
                                                    )
                                                    Spacer(Modifier.height(10.dp))
                                                    Row {
                                                        Button(
                                                            modifier = Modifier.width(115.dp),
                                                            onClick = {
                                                                expanded = false
                                                                viewModel.submitAnswer(true)
                                                            }
                                                        ) {
                                                            Text(
                                                                text = stringResource(R.string.right_answer_button_text)
                                                            )
                                                        }
                                                        Spacer(modifier = Modifier.width(15.dp))
                                                        OutlinedButton(
                                                            modifier = Modifier.width(115.dp),
                                                            onClick = {
                                                                expanded = false
                                                                viewModel.submitAnswer(false)
                                                            }
                                                        ) {
                                                            Text(
                                                                text = stringResource(R.string.wrong_answer_button_text)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}