package com.jobinterviewapp.presentation.interview.interview_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.use_case.interview_simulation.GetInterviewResultUseCase
import com.jobinterviewapp.domain.use_case.user.AddTaskToFavoritesUseCase
import com.jobinterviewapp.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewResultViewModel @Inject constructor(
    private val getInterviewResult: GetInterviewResultUseCase,
    savedStateHandle: SavedStateHandle,
    private val dataStoreManager: AppModule.DataStoreManager,
    private val addTaskToFavoritesUseCase: AddTaskToFavoritesUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(InterviewResultState())
    val state = _state.asStateFlow()
    private val interviewId = savedStateHandle.get<Int>(Constants.PARAM_INTERVIEW_ID)
    private val professionId = savedStateHandle.get<Int>(Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY_ID)

    init {
        loadInterviewResult()
    }

    fun addTaskToFavorites(taskId: Int) {
        viewModelScope.launch {
            val userKey = state.value.userKey ?: return@launch
            addTaskToFavoritesUseCase(
                userKey = userKey,
                taskId = taskId
            ).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                            )
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }
    fun loadInterviewResult() {
        viewModelScope.launch {
            if(interviewId == null)
                return@launch
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            dataStoreManager.authSettings.collectLatest { authSettings ->
                authSettings.userKey?.let { userKey ->
                    getInterviewResult(
                        interviewId = interviewId,
                        userKey = userKey,
                    ).collectLatest { result ->
                        when(result) {
                            is Resource.Success -> {
                                _state.update {
                                    val questionsCount = result.data.countRight + result.data.countWrong
                                    val rightAnswersPercentage = (result.data.countRight.toFloat() / questionsCount)
                                    it.copy(
                                        userKey = userKey,
                                        wrongAnswers = result.data.wrongQuestions,
                                        wrongAnswersCount = result.data.countWrong,
                                        rightAnswersCount = result.data.countRight,
                                        rightAnswersPercentage = rightAnswersPercentage,
                                        answersCount = questionsCount,
                                        professionId = professionId,
                                        isLoading = false,
                                        error = null
                                    )
                                }
                            }
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        error = result.message,
                                        isLoading = false,
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