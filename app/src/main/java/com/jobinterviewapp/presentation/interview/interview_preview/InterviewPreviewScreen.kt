package com.jobinterviewapp.presentation.interview.interview_preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.interview.components.InterviewConfigurationTopBar

@Composable
fun InterviewPreviewScreen(
    navController: NavController,
    viewModel: InterviewPreviewViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            InterviewConfigurationTopBar(
                navController = navController,
                screen = Screen.InterviewPreviewScreen,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier,
            ) {
                Card(
                    elevation = 0.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp,
                        topStart = 0.dp,
                        topEnd = 0.dp
                    ),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        state.previewName?.let { previewName ->
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp),
                                text = previewName,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.primary,
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = stringResource(R.string.interview_simulation_subtitle),
                                style = MaterialTheme.typography.body1,
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    item {
                        Card(
                            shape = RoundedCornerShape(
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp,
                                topStart = 10.dp,
                                topEnd = 10.dp
                            ),
                            elevation = 0.dp,
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 16.dp),
                            ) {
                                state.questionsCount?.let { questionsCount ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.ic_questions_count),
                                            contentDescription = null,
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(R.string.questions_count, questionsCount),
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        text = stringResource(id = R.string.questions_count_content, questionsCount),
                                        color = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .background(color = MaterialTheme.colors.surface)
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_tip_number_1),
                                    contentDescription = null,
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.tip_number_1),
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.tip_number_1_content),
                                color = Color.DarkGray
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .background(color = MaterialTheme.colors.surface)
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_tip_number_2),
                                    contentDescription = null,
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.tip_number_2),
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.tip_number_2_content),
                                color = Color.DarkGray
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .background(color = MaterialTheme.colors.surface)
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_tip_number_3),
                                    contentDescription = null,
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.tip_number_3),
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = stringResource(id = R.string.tip_number_3_content),
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
        }
    }
}