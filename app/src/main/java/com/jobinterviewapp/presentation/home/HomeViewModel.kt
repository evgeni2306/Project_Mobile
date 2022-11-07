package com.jobinterviewapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.interviewConfiguration.GetDirectionsOfFieldUseCase
import com.jobinterviewapp.domain.use_case.interviewConfiguration.GetFieldOfActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFieldOfActivityUseCase: GetFieldOfActivityUseCase,
    private val getDirectionsOfFieldUseCase: GetDirectionsOfFieldUseCase,
): ViewModel() {

    fun loadFieldOfActivity() {
        viewModelScope.launch {
            getFieldOfActivityUseCase().collectLatest {

            }

            getDirectionsOfFieldUseCase(1).collectLatest {

            }
        }
    }

    init {
        loadFieldOfActivity()
    }
}