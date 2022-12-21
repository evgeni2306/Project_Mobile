package com.jobinterviewapp.presentation.interview.interview_configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.interview_configuration.GetFieldsOfActivityUseCase
import com.jobinterviewapp.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FieldsViewModel @Inject constructor(
    private val getFieldOfActivityUseCase: GetFieldsOfActivityUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(InterviewConfigurationState())
    val state = _state.asStateFlow()

    fun loadFieldsOfActivity() {
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                fieldsOfActivity = emptyList(),
            )
        }
        viewModelScope.launch {
            getFieldOfActivityUseCase().collectLatest { result ->
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
                                isLoading = false,
                                error = result.message,
                            )
                        }
                    }
                }
            }
        }
    }

    init {
        loadFieldsOfActivity()
    }
}