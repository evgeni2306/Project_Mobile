package com.jobinterviewapp.presentation.favorite_tasks

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.remote.dto.FavoriteTaskDto

data class FavoriteTasksScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val taskList: List<FavoriteTaskDto> = emptyList(),
    val userKey: String? = null,
    val openedTask: FavoriteTaskDto? = null,
    val openedTaskIndex: Int? = null,
)