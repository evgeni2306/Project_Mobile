package com.jobinterviewapp.presentation.favorite_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.models.FavoriteTask
import com.jobinterviewapp.domain.use_case.user.DeleteTaskFromFavoritesUseCase
import com.jobinterviewapp.domain.use_case.user.GetFavoriteTaskListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavoriteTasksViewModel @Inject constructor(
    private val getFavoriteTaskListUseCase: GetFavoriteTaskListUseCase,
    private val deleteTaskFromFavoritesUseCase: DeleteTaskFromFavoritesUseCase,
    private val dataStoreManager: AppModule.DataStoreManager,
): ViewModel() {
    private val _state = MutableStateFlow(FavoriteTasksScreenState())

    val state = _state.asStateFlow()

    init {
        loadTaskList()
    }

    fun onTaskClick(index: Int) {
        viewModelScope.launch {
            if(state.value.selectedTasksIdList.isNotEmpty()) {
                onLongClick(index)
            }
            else {
                _state.update {
                    it.copy(
                        openedTaskIndex = index,
                        openedTask = it.taskList[index],
                    )
                }
            }
        }
    }

    fun onLongClick(index: Int) {
        viewModelScope.launch {
            val state = state.value
            val task = state.taskList[index]
            _state.update {
                it.copy(
                    selectedTasksIdList = if(!task.isSelected)
                        state.selectedTasksIdList.plus(task.favoriteId)
                    else
                        state.selectedTasksIdList.minus(task.favoriteId)
                )
            }
            val newTaskList = state.taskList.mapIndexed { taskIndex, favoriteTask ->
                if(index == taskIndex) {
                    favoriteTask.copy(
                        isSelected = !favoriteTask.isSelected
                    )
                }
                else {
                    favoriteTask
                }
            }

            _state.update {
                it.copy(
                    taskList = newTaskList,
                )
            }
        }
    }

    fun onDeleteConfirmClick() {
        val state = state.value
        if(state.userKey == null)
            return
        val newTaskList: List<FavoriteTask> = if(state.openedTask != null) {
            state.taskList.minus(state.openedTask)
        } else {
            state.taskList.filter { task ->
                !state.selectedTasksIdList.contains(task.favoriteId)
            }
        }

        _state.update {
            it.copy(
                taskList = newTaskList,
                openedTask = null,
                isOpenedDeleteDialog = false
            )
        }
        viewModelScope.launch {
            deleteTaskFromFavoritesUseCase(
                userKey = state.userKey,
                favoriteIdList = if(state.openedTask == null)
                    state.selectedTasksIdList
                else listOf(state.openedTask.favoriteId)
            ).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                selectedTasksIdList = emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    fun onDeleteDialogHideClick() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isOpenedDeleteDialog = false,
                )
            }
        }
    }

    fun onDialogHideClick() {
        _state.update {
            it.copy(
                openedTask = null,
            )
        }
    }

    fun onTaskDelete() {
        _state.update {
            it.copy(
                isOpenedDeleteDialog = true,
            )
        }
    }

    private fun loadTaskList() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            dataStoreManager.authSettings.collectLatest { authSettings ->
                authSettings.userKey?.let { userKey ->
                    _state.update {
                        it.copy(
                            userKey = userKey
                        )
                    }
                    getFavoriteTaskListUseCase(userKey).collect { result ->
                        when(result) {
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        taskList = result.data
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message
                                    )
                                }
                            }
                        }
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun onRefresh() {
        loadTaskList()
    }
}