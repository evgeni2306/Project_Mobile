package com.jobinterviewapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.interviewConfiguration.GetDirectionsOfFieldUseCase
import com.jobinterviewapp.domain.use_case.interviewConfiguration.GetFieldsOfActivityUseCase
import com.weatherapp.core.util.Resource
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

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun loadFieldsOfActivity() {
        viewModelScope.launch {
            getFieldOfActivityUseCase().collectLatest { result ->
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

    init {
        loadFieldsOfActivity()
    }
}