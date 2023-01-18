package com.jobinterviewapp.presentation.favorite_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.use_case.user.GetFavoriteTaskListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteTasksViewModel @Inject constructor(
    private val getFavoriteTaskListUseCase: GetFavoriteTaskListUseCase,
    private val dataStoreManager: AppModule.DataStoreManager,
): ViewModel() {
    private val _state = MutableStateFlow(FavoriteTasksScreenState())

    val state = _state.asStateFlow()

    fun onTaskClick(index: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    openedTaskIndex = index,
                    openedTask = it.taskList[index],
                )
            }
        }
    }

    fun onDialogHideClick() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    openedTask = null,
                    openedTaskIndex = null,
                )
            }
        }
    }

    fun onTaskDelete(index: Int) {
        viewModelScope.launch {

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