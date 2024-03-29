package com.jobinterviewapp.presentation.interview.interview_configuration.interview_preview

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.DefaultTopBar
import com.jobinterviewapp.presentation.components.ErrorTextHandler
import com.jobinterviewapp.presentation.components.TopBarTitleText
import com.jobinterviewapp.presentation.interview.interview_configuration.interview_preview.components.TipListItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun InterviewPreviewScreen(
    navController: NavController,
    viewModel: InterviewPreviewViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        TopBarTitleText(text = stringResource(R.string.interview_simulation_subtitle))
                        state.previewName?.let { previewName ->
                            Text(
                                text = previewName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
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
        },
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(state.error == null) {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .fillMaxWidth()
                        ,
                        onClick = {
                            navController.navigate(Screen.InterviewSimulationScreen.withArgs(
                                state.professionId.toString(),
                                state.questionsCount.toString(),
                            )) {
                                navController.currentBackStackEntry?.destination?.route?.let { currentRoute ->
                                    popUpTo(
                                        route = currentRoute,
                                    ) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.start_interview_button_text)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = 53.dp
                )
                .fillMaxSize(),
        ) {
            if(state.error != null) {
                ErrorTextHandler(
                    error = state.error,
                    modifier = Modifier.align(Alignment.Center),
                    onRefreshClick = viewModel::loadInterviewPreview
                )
                return@Box
            }
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides OverscrollConfiguration(
                    glowColor = MaterialTheme.colorScheme.background,
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    TipListItem(
                        title = stringResource(R.string.questions_count, state.questionsCount?:0),
                        description = stringResource(
                            id = R.string.questions_count_content,
                            state.questionsCount?:0),
                        trailingImageId = R.drawable.ic_questions_count,
                        animationDuration = 600,
                    )

                    TipListItem(
                        title = stringResource(R.string.tip_number_1),
                        description = stringResource(R.string.tip_number_1_content),
                        trailingImageId = R.drawable.ic_tip_number_1,
                        animationDuration = 800,
                    )

                    TipListItem(
                        title = stringResource(R.string.tip_number_2),
                        description = stringResource(R.string.tip_number_2_content),
                        trailingImageId = R.drawable.ic_tip_number_2,
                        animationDuration = 1000,
                    )

                    TipListItem(
                        title = stringResource(R.string.tip_number_3),
                        description = stringResource(R.string.tip_number_3_content),
                        trailingImageId = R.drawable.ic_tip_number_3,
                        animationDuration = 1200,
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                }
            }
        }
    }
}