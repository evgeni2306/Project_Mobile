package com.jobinterviewapp.presentation.knowledge_base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobinterviewapp.R
import com.jobinterviewapp.presentation.Screen
import com.jobinterviewapp.presentation.components.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun KnowledgeBaseScreen(
    viewModel: KnowledgeBaseViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = {
                    TopBarTitleText(Screen.KnowledgeBaseScreen.screenName.asString())
                },
                onProfileClick = {},
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize(),
        ) {
            ErrorTextHandler(
                error = state.error,
                modifier = Modifier
                    .align(Alignment.Center),
                onRefreshClick = viewModel::onRefreshPage
            )
            if (state.error == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    val isSearchVisible = remember { MutableTransitionState(false) }
                    ElevatedCard {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            ListItem(
                                modifier = Modifier
                                    .clickable {
                                        isSearchVisible.targetState = !isSearchVisible.currentState
                                    }
                                    .height(45.dp)
                                ,
                                headlineText = {
                                    Text(
                                        text = if(state.currentProfession == null)
                                            stringResource(R.string.choose_profession_search_text)
                                        else
                                            state.currentProfession.name,
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        contentDescription = null,
                                        imageVector = if(isSearchVisible.targetState)
                                            Icons.Default.KeyboardArrowUp
                                        else {
                                            Icons.Default.KeyboardArrowDown
                                        }
                                    )
                                }
                            )
                            AnimatedVisibility(
                                visibleState = isSearchVisible,
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    DefaultSearch(
                                        modifier = Modifier
                                            .padding(vertical = 6.dp)
                                            .height(40.dp)
                                            .fillMaxWidth()
                                            .padding(horizontal = 13.dp)
                                        ,
                                        searchValue = state.searchValue,
                                        onValueChange = viewModel::onSearchValueChange,
                                        onSearchClear = viewModel::onSearchClear
                                    )
                                    CompositionLocalProvider(
                                        LocalOverscrollConfiguration provides OverscrollConfiguration(
                                            glowColor = MaterialTheme.colorScheme.background,
                                        )
                                    ) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(0.dp, 180.dp)
                                            ,
                                        ) {
                                            items(state.professionList) { profession ->
                                                ListItem(
                                                    modifier = Modifier
                                                        .clickable {
                                                            isSearchVisible.targetState = false
                                                            viewModel.onProfessionClick(profession)
                                                        }
                                                        .height(40.dp)
                                                        .padding(start = 5.dp)
                                                    ,
                                                    headlineText = {
                                                        Text(
                                                            text = profession.name
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(state.currentProfession != null && !state.isLoading) {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .align(Alignment.Start),
                            text = stringResource(R.string.tasks_found_text, state.currentProfessionTaskList.size),
                        )
                        Divider(
                            thickness = 1.dp,
                        )
                        CompositionLocalProvider(
                            LocalOverscrollConfiguration provides OverscrollConfiguration(
                                glowColor = MaterialTheme.colorScheme.background,
                            )
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                ,
                            ) {
                                items(state.currentProfessionTaskList.size) { taskIndex ->
                                    val task = state.currentProfessionTaskList[taskIndex]
                                    TaskItemCard(
                                        task = task,
                                        onFavoriteTaskClicked = { (viewModel::onFavoriteTaskClicked)(taskIndex) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}