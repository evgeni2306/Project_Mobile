package com.jobinterviewapp.presentation.favorite_tasks

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.domain.models.FavoriteTask

data class FavoriteTasksScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val taskList: List<FavoriteTask> = emptyList(),
    val userKey: String? = null,
    val openedTask: FavoriteTask? = null,
    val openedTaskIndex: Int? = null,
    val isOpenedDeleteDialog: Boolean = false,
    val selectedTasksIdList: List<Int> = emptyList(),
)