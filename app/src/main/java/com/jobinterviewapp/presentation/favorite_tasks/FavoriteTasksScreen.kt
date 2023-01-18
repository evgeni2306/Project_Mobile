package com.jobinterviewapp.presentation.favorite_tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jobinterviewapp.R
import com.jobinterviewapp.domain.models.Task
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.DefaultTopBar
import com.jobinterviewapp.presentation.components.TaskItem
import com.jobinterviewapp.presentation.components.TaskItemCard
import com.jobinterviewapp.presentation.components.TopBarTitleText
import com.jobinterviewapp.presentation.favorite_tasks.components.FavoriteTaskCard
import com.jobinterviewapp.presentation.favorite_tasks.components.FavoriteTaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTasksScreen(
    navController: NavController,
    viewModel: FavoriteTasksViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = {
                    TopBarTitleText(Screen.FavoriteTasksScreen.name)
                },
                onProfileClick = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
            )
        ) {
            val state = viewModel.state.collectAsState().value
            LazyColumn() {
                items(state.taskList.size) { favoriteTaskIndex ->
                    val task = state.taskList[favoriteTaskIndex]
                    FavoriteTaskCard(
                        task = task,
                        modifier = Modifier
                            .clickable {
                                viewModel.onTaskClick(favoriteTaskIndex)
                            }
                            ,
                        onDeleteTaskClick = {
                            viewModel.onTaskDelete(favoriteTaskIndex)
                        }
                    )
                }
            }

            if(state.openedTask != null) {
                AlertDialog(
                    onDismissRequest = viewModel::onDialogHideClick,
                    title = {
                        FavoriteTaskItem(
                            task = state.openedTask,
                            onDeleteTaskClick = { (viewModel::onTaskDelete)(state.openedTaskIndex!!) },
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = viewModel::onDialogHideClick,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth()
                            ,
                        ) {
                            Text(
                                text = stringResource(R.string.ok_text)
                            )
                        }
                    },
                    text = {
                        Text(
                            text = state.openedTask.answer,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                )
            }
        }
    }
}