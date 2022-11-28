package com.jobinterviewapp.presentation.interview_simulation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.interview_simulation.components.TaskCategoryElement

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
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
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
                        onClick = {},
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
                        modifier = Modifier.size(100.dp),
                        painter = painterResource(id = R.drawable.ic_employer),
                        contentDescription = null,
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ElevatedCard(
                            modifier = Modifier
                                .height(25.dp)
                                .width(50.dp),
                            shape = TriangleShape(),
                        ) {}
                        var expanded by remember { mutableStateOf(false) }
                        ElevatedCard(
                            modifier = Modifier
                                .animateContentSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
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
                                        onClick = { /*TODO*/ },
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_favorites),
                                            contentDescription = null
                                        )
                                    }
                                }
                                Text(
                                    style = MaterialTheme.typography.titleSmall,
                                    text = currentTask.question,
                                )
                                Button(
                                    modifier = Modifier
                                        .padding(vertical = 10.dp),
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
                                            )
                                            Text(
                                                text = stringResource(R.string.is_right_answer_text)
                                            )
                                            Spacer(Modifier.height(10.dp))
                                            Row {
                                                Button(
                                                    modifier = Modifier.width(150.dp),
                                                    onClick = {
                                                        viewModel.submitAnswer(true)
                                                    }
                                                ) {
                                                    Text(
                                                        text = stringResource(R.string.right_answer_button_text)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(15.dp))
                                                OutlinedButton(
                                                    modifier = Modifier.width(150.dp),
                                                    onClick = {
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