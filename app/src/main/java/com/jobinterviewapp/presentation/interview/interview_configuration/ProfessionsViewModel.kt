package com.jobinterviewapp.presentation.interview.interview_configuration

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.interview_configuration.GetProfessionsOfTechnologyUseCase
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
class ProfessionsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProfessionsOfTechnologyUseCase: GetProfessionsOfTechnologyUseCase
): ViewModel() {
    private val _state = MutableStateFlow(InterviewConfigurationState())
    val state = _state.asStateFlow()
    private val technologyId: Int? = savedStateHandle.get<Int>(Constants.PARAM_TECHNOLOGIES_OF_DIRECTION_ID)

    fun loadDirectionsOfField() {
        viewModelScope.launch {
            if(technologyId == null) {
                return@launch
            }
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    fieldsOfActivity = emptyList(),
                )
            }
            getProfessionsOfTechnologyUseCase(technologyId).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update { it.copy(
                            fieldsOfActivity = result.data,
                            error = null,
                            isLoading = false,
                        ) }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = result.message,
                                isLoading = false,
                                fieldsOfActivity = emptyList()
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