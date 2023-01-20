package com.jobinterviewapp.presentation.interview.interview_simulation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.use_case.interview_simulation.GetInterviewTaskUseCase
import com.jobinterviewapp.domain.use_case.interview_simulation.PostInterviewTaskAnswerUseCase
import com.jobinterviewapp.domain.use_case.interview_simulation.StartInterviewUseCase
import com.jobinterviewapp.domain.use_case.user.AddTaskToFavoritesUseCase
import com.jobinterviewapp.domain.use_case.user.DeleteTaskFromFavoritesUseCase
import com.jobinterviewapp.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewSimulationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val startInterviewUseCase: StartInterviewUseCase,
    private val dataStoreManager: AppModule.DataStoreManager,
    private val getInterviewTaskUseCase: GetInterviewTaskUseCase,
    private val postInterviewTaskAnswerUseCase: PostInterviewTaskAnswerUseCase,
    private val addTaskToFavoritesUseCase: AddTaskToFavoritesUseCase,
    private val deleteTaskFromFavoritesUseCase: DeleteTaskFromFavoritesUseCase,
): ViewModel() {
    private val professionId = savedStateHandle.get<Int>(Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY_ID)
    private val interviewTaskCount: Int? = savedStateHandle.get<Int>(Constants.PARAM_INTERVIEW_TASK_COUNT)
    private val _state = MutableStateFlow(InterviewSimulationState())
    val state = _state.asStateFlow()

    init {
        startInterview()
    }

    fun startInterview() {
        professionId?.let {
            viewModelScope.launch {
                _state.update {
                    it.copy(
                        error = null,
                        isLoading = true,
                    )
                }
                dataStoreManager.authSettings.collectLatest { authSettings ->
                    authSettings.userKey?.let { userKey ->
                        startInterviewUseCase(professionId, userKey).collectLatest { result ->
                            when(result) {
                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            professionId = professionId,
                                            interviewId = result.data,
                                            error = null,
                                            userKey = authSettings.userKey,
                                        )
                                    }
                                    getInterviewTask()
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
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onFavoriteTaskClicked() {
        viewModelScope.launch {
            val userKey = state.value.userKey ?: return@launch
            var currentTask = state.value.currentTask ?: return@launch
            if(currentTask.isFavorite) {
                if (currentTask.favoriteId == null) {
                    return@launch
                }
                deleteTaskFromFavoritesUseCase(
                    userKey,
                    listOf(currentTask.favoriteId!!)
                ).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    error = result.message,
                                )
                            }
                        }
                        is Resource.Success -> {
                            currentTask = currentTask.copy(
                                favoriteId = null,
                                isFavorite = !currentTask.isFavorite,
                            )
                        }
                    }
                }
                _state.update {
                    it.copy(
                        currentTask = currentTask,
                    )
                }
            } else {
                var newFavoriteId: Int? = null
                addTaskToFavoritesUseCase(userKey, currentTask.questionId).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    error = result.message,
                                )
                            }
                        }
                        is Resource.Success -> {
                            newFavoriteId = result.data
                        }
                    }
                    newFavoriteId?.let {
                        currentTask = currentTask.copy(
                            isFavorite = !currentTask.isFavorite,
                            favoriteId = newFavoriteId
                        )
                        _state.update {
                            it.copy(
                                currentTask = currentTask,
                            )
                        }
                    }
                }
            }
        }
    }

    fun submitAnswer(isRightAnswer: Boolean) {
        val stateValue = state.value
        if(interviewTaskCount == null
            || stateValue.userKey == null
            || stateValue.currentTask == null)
            return
        viewModelScope.launch {
            postInterviewTaskAnswerUseCase(
                taskId = stateValue.currentTask.taskId,
                userKey = stateValue.userKey,
                answer = isRightAnswer,
            ).collect { result ->
                when(result) {
                    is Resource.Success -> {
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message,
                            )
                        }
                    }
                }
                getInterviewTask()
            }
        }
    }

    private fun getInterviewTask() {
        val stateValue = state.value
        if(interviewTaskCount == null
            || stateValue.userKey == null
            || stateValue.interviewId == null)
            return
        viewModelScope.launch {
            val loadingJob = launch {
                delay(Constants.SIMULATION_LOADING_ANIMATION_DELAY)
                _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }
            }
            getInterviewTaskUseCase(
                stateValue.userKey,
                stateValue.interviewId).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        if(result.data == null) {
                            _state.update {
                                it.copy(
                                    isEndOfInterview = true,
                                    error = null,
                                )
                            }
                        }
                        else {
                            _state.update {
                                it.copy(
                                    currentTask = result.data,
                                    error = null,
                                )
                            }
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
                loadingJob.cancel()
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}