package com.jobinterviewapp.presentation.interview.interview_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.use_case.interview_simulation.GetInterviewResultUseCase
import com.jobinterviewapp.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewResultViewModel @Inject constructor(
    private val getInterviewResult: GetInterviewResultUseCase,
    savedStateHandle: SavedStateHandle,
    private val dataStoreManager: AppModule.DataStoreManager,
): ViewModel() {
    private val _state = MutableStateFlow(InterviewResultState())
    val state = _state.asStateFlow()
    private val interviewId = savedStateHandle.get<Int>(Constants.PARAM_INTERVIEW_ID)
    private val professionId = savedStateHandle.get<Int>(Constants.PARAM_PROFESSIONS_OF_TECHNOLOGY_ID)

    init {
        loadInterviewResult()
    }
    fun loadInterviewResult() {
        viewModelScope.launch {
            if(interviewId == null)
                return@launch
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
                                        wrongAnswers = result.data.wrongQuestions,
                                        wrongAnswersCount = result.data.countWrong,
                                        rightAnswersCount = result.data.countRight,
                                        rightAnswersPercentage = rightAnswersPercentage,
                                        answersCount = questionsCount,
                                        professionId = professionId,
                                    )
                                }
                            }
                            is Resource.Error -> {

                            }
                        }
                    }
                }
            }
        }
    }
}