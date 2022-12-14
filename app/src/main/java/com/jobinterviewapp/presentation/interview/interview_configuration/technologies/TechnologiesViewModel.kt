package com.jobinterviewapp.presentation.interview.interview_configuration.technologies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.interview_configuration.GetTechnologiesOfDirectionUseCase
import com.jobinterviewapp.presentation.Constants
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.presentation.interview.interview_configuration.InterviewConfigurationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TechnologiesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTechnologiesOfDirectionUseCase: GetTechnologiesOfDirectionUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(InterviewConfigurationState())
    val state = _state.asStateFlow()
    private val directionId: Int? = savedStateHandle.get<Int>(Constants.PARAM_DIRECTION_OF_FIELD_ID)

    fun loadDirectionsOfField() {
        viewModelScope.launch {
            if(directionId == null) {
                return@launch
            }
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    fieldsOfActivity = emptyList(),
                )
            }
            getTechnologiesOfDirectionUseCase(directionId).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update { it.copy(
                            fieldsOfActivity = result.data,
                            isLoading = false,
                            error = null,
                        ) }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message,
                                fieldsOfActivity = emptyList(),
                            )
                        }
                    }
                }
            }
        }
    }

    init {
        loadDirectionsOfField()
    }
}