package com.jobinterviewapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.domain.use_case.interviewConfiguration.GetFieldOfActivityUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getFieldOfActivityUseCase: GetFieldOfActivityUseCase
): ViewModel() {

    fun loadFieldOfActivity() {
        viewModelScope.launch {
            getFieldOfActivityUseCase().collectLatest {

            }
        }
    }

    init {
        loadFieldOfActivity()
    }
}