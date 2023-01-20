package com.jobinterviewapp.presentation.knowledge_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.ProfessionDto
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.models.Task
import com.jobinterviewapp.domain.use_case.knowledge_base.GetProfessionListUseCase
import com.jobinterviewapp.domain.use_case.knowledge_base.GetProfessionTaskListUseCase
import com.jobinterviewapp.domain.use_case.user.AddTaskToFavoritesUseCase
import com.jobinterviewapp.domain.use_case.user.DeleteTaskFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KnowledgeBaseViewModel @Inject constructor(
    private val getProfessionListUseCase: GetProfessionListUseCase,
    private val dataStoreManager: AppModule.DataStoreManager,
    private val addTaskToFavoritesUseCase: AddTaskToFavoritesUseCase,
    private val deleteTaskFromFavoritesUseCase: DeleteTaskFromFavoritesUseCase,
    private val getProfessionTaskListUseCase: GetProfessionTaskListUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(KnowledgeBaseState())
    val state = _state.asStateFlow()

    private val defaultProfessionList = MutableStateFlow<List<ProfessionDto>>(emptyList())

    init {
        getProfessionList()
    }

    fun onSearchClear() {
        onSearchValueChange("")
    }

    fun onProfessionClick(profession: ProfessionDto) {
        viewModelScope.launch {
            val userKey = state.value.userKey ?: return@launch
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }
            _state.update {
                it.copy(
                    currentProfession = profession,
                )
            }
            getProfessionTaskListUseCase(
                userKey,
                profession.profId
            ).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                currentProfessionTaskList = result.data,
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message,
                            )
                        }
                    }
                }
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun onSearchValueChange(value: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    searchValue = value,
                )
            }
            if(value.isEmpty()) {
                _state.update {
                    it.copy(
                        professionList = defaultProfessionList.value,
                    )
                }
            }
            val filteredProfessionList = defaultProfessionList.value.filter { profession ->
                profession.name.contains(value, true)
            }
            _state.update {
                it.copy(
                    professionList = filteredProfessionList,
                )
            }
        }
    }

    fun onRefreshPage() {
        _state.update {
            it.copy(
                error = null,
            )
        }
        val currentProfession = state.value.currentProfession
        if(currentProfession != null) {
            onProfessionClick(currentProfession)
        }
        else {
            getProfessionList()
        }
    }

    fun onTaskClick(task: Task, taskIndex: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    openedTask = task,
                    openedTaskIndex = taskIndex,
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

    private fun getProfessionList() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            dataStoreManager.authSettings.collect { authSettings ->
                if(authSettings.userKey == null) {
                    return@collect
                }
                _state.update {
                    it.copy(
                        userKey = authSettings.userKey,
                    )
                }
                getProfessionListUseCase(authSettings.userKey).collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            defaultProfessionList.value = result.data
                            _state.update {
                                it.copy(
                                    professionList = result.data
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

    fun onFavoriteTaskClicked(taskIndex: Int) {
        viewModelScope.launch {
            val userKey = state.value.userKey ?: return@launch
            var newTask: Task? = null
            val taskList = state.value.currentProfessionTaskList
                .mapIndexed { index, task ->
                    if (index == taskIndex) {
                        if (task.isFavorite) {
                            if (task.favoriteId == null) {
                                return@launch
                            }
                            deleteTaskFromFavoritesUseCase(
                                userKey,
                                listOf(task.favoriteId)
                            ).collect { result ->
                                when (result) {
                                    is Resource.Error -> {
                                        _state.update {
                                            it.copy(
                                                error = result.message
                                            )
                                        }
                                    }
                                    is Resource.Success -> {
                                    }
                                }
                            }
                            newTask = task.copy(
                                isFavorite = false,
                                favoriteId = null,
                            )
                            task.copy(
                                isFavorite = false,
                                favoriteId = null,
                            )
                        } else {
                            var newFavoriteId = 0
                            addTaskToFavoritesUseCase(userKey, task.questionId).collect { result ->
                                when (result) {
                                    is Resource.Error -> {
                                    }
                                    is Resource.Success -> {
                                        newFavoriteId = result.data
                                    }
                                }
                            }
                            newTask = task.copy(
                                favoriteId = newFavoriteId,
                                isFavorite = true
                            )
                            task.copy(
                                favoriteId = newFavoriteId,
                                isFavorite = true
                            )
                        }
                    } else {
                        task
                    }
                }
            val selectedTask = state.value.openedTask
            if(selectedTask != null && newTask != null) {
                _state.update {
                    it.copy(
                        openedTask = newTask
                    )
                }
            }
            _state.update {
                it.copy(
                    currentProfessionTaskList = taskList
                )
            }
        }
    }
}