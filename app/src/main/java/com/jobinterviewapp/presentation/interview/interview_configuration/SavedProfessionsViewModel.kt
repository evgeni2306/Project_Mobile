package com.jobinterviewapp.presentation.interview.interview_configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.di.AppModule
import com.jobinterviewapp.domain.use_case.interview_configuration.GetSavedProfessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedProfessionsViewModel @Inject constructor(
    val getSavedProfessionsUseCase: GetSavedProfessionsUseCase,
    private val dataStoreManager: AppModule.DataStoreManager,
): ViewModel() {

    private val _state = MutableStateFlow(SavedProfessionsState())
    val state = _state.asStateFlow()

    init {
        getSavedProfessions()
    }

    fun getSavedProfessions() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    savedProfessions = emptyList(),
                )
            }
            dataStoreManager.authSettings.collectLatest { authSettings ->
                if(authSettings.userKey == null)
                    return@collectLatest
                _state.update {
                    it.copy(
                        userKey = authSettings.userKey
                    )
                }
                getSavedProfessionsUseCase(authSettings.userKey).collectLatest { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    savedProfessions = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    savedProfessions = emptyList(),
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