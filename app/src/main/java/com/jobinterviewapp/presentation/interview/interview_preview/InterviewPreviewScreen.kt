package com.jobinterviewapp.presentation.interview.interview_preview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewPreviewScreen(
    navController: NavController,
    viewModel: InterviewPreviewViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        state.previewName?.let { previewName ->
                            Text(
                                text = previewName,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            text = stringResource(R.string.interview_simulation_subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                },

                actions = {
                    IconButton(
                        onClick = {}
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
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                Text(
//                    text = stringResource(R.string.interview_simulation_subtitle),
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.SemiBold,
//                )

                val isVisible = remember {
                    MutableTransitionState(false).apply {
                        // Start the animation immediately.
                        this.targetState = true
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    AnimatedVisibility(
                        visibleState = isVisible,
                        enter = slideInHorizontally(
                            animationSpec = tween(600, 0),
                            initialOffsetX = {
                                it
                            }
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                            ,
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
                                )
                            }
                        }
                    }

                    AnimatedVisibility(
                        visibleState = isVisible,
                        enter = slideInHorizontally(
                            animationSpec = tween(800, 0),
                            initialOffsetX = {
                                it
                            }
                        )
                    ) {
                        Column(
                            modifier = Modifier
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
                            )
                        }
                    }

                    AnimatedVisibility(
                        visibleState = isVisible,
                        enter = slideInHorizontally(
                            animationSpec = tween(1000, 0),
                            initialOffsetX = {
                                it
                            }
                        )
                    ) {
                        Column(
                            modifier = Modifier
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
                            )
                        }
                    }

                    AnimatedVisibility(
                        visibleState = isVisible,
                        enter = slideInHorizontally(
                            animationSpec = tween(1200, 0),
                            initialOffsetX = {
                                it
                            }
                        )
                    ) {
                        Column(
                            modifier = Modifier
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
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(22.dp))
                }
            }
        }
    }
}