package com.jobinterviewapp.presentation.interview_simulation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.use_case.interview_simulation.GetInterviewTaskUseCase
import com.jobinterviewapp.domain.use_case.interview_simulation.PostInterviewTaskAnswerUseCase
import com.jobinterviewapp.domain.use_case.interview_simulation.StartInterviewUseCase
import com.jobinterviewapp.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
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
                dataStoreManager.authSettings.collectLatest { authSettings ->
                    authSettings.userKey?.let { userKey ->
                        startInterviewUseCase(professionId, userKey).collectLatest { result ->
                            when(result) {
                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            interviewId = result.data,
                                            error = null,
                                            userKey = authSettings.userKey
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
            getInterviewTaskUseCase(
                stateValue.userKey,
                stateValue.interviewId).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                currentTask = result.data,
                                error = null,
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
            }
        }
    }
}