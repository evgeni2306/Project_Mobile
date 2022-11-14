package com.jobinterviewapp.presentation.interview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.interviewConfiguration.GetTechnologiesOfDirectionUseCase
import com.jobinterviewapp.presentation.Constants
import com.jobinterviewapp.core.util.Resource
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
    private val _state = MutableStateFlow(InterviewState())
    val state = _state.asStateFlow()
    private val directionId: Int? = savedStateHandle.get<Int>(Constants.PARAM_DIRECTION_OF_FIELD_ID)

    fun loadDirectionsOfField() {
        viewModelScope.launch {
            directionId?.let {
                getTechnologiesOfDirectionUseCase(directionId).collectLatest { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.update { it.copy(
                                fieldsOfActivity = result.data
                            ) }
                        }
                        is Resource.Error -> {
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