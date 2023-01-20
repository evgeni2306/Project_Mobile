package com.jobinterviewapp.presentation.favorite_tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.DefaultTopBar
import com.jobinterviewapp.presentation.components.ErrorTextHandler
import com.jobinterviewapp.presentation.components.TopBarTitleText
import com.jobinterviewapp.presentation.favorite_tasks.components.FavoriteTaskCard
import com.jobinterviewapp.presentation.favorite_tasks.components.FavoriteTaskItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FavoriteTasksScreen(
    viewModel: FavoriteTasksViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = {
                    TopBarTitleText(Screen.FavoriteTasksScreen.screenName.asString())
                },
                actions = {
                    if(state.selectedTasksIdList.isEmpty()) {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = null,
                            )
                        }
                    }
                    else {
                        IconButton(
                            onClick = viewModel::onTaskDelete
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = MaterialTheme.colorScheme.error,
                                contentDescription = null,
                            )
                        }
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                top = innerPadding.calculateTopPadding(),
            )
        ) {
            if(state.error == null && !state.isLoading) {
                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    items(state.taskList.size) { favoriteTaskIndex ->
                        val task = state.taskList[favoriteTaskIndex]
                        FavoriteTaskCard(
                            task = task,
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = {
                                        viewModel.onTaskClick(favoriteTaskIndex)
                                    },
                                    onLongClick = {
                                        viewModel.onLongClick(favoriteTaskIndex)
                                    },
                                )
                        )
                    }
                }

                if(state.isOpenedDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = viewModel::onDeleteDialogHideClick,
                        title = {
                            Text(
                                text = stringResource(R.string.delete_task_dialog_title),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        },
                        dismissButton = {
                            TextButton(
                                onClick = viewModel::onDeleteDialogHideClick,
                                modifier = Modifier
                                ,
                            ) {
                                Text(
                                    text = stringResource(R.string.cancel_button_text)
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = viewModel::onDeleteConfirmClick,
                                modifier = Modifier
                                ,
                            ) {
                                Text(
                                    text = stringResource(R.string.delete_confirm_text),
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(R.string.delete_task_dialog_text),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    )
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
            ErrorTextHandler(
                error = state.error,
                modifier = Modifier.padding(16.dp).align(Alignment.Center),
                onRefreshClick = viewModel::onRefresh
            )
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}