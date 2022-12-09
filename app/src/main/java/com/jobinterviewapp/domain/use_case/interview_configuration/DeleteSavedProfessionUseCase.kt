package com.jobinterviewapp.domain.use_case.interview_configuration

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.repository.InterviewConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSavedProfessionUseCase @Inject constructor(
    private val repository: InterviewConfigurationRepository
) {

    operator fun invoke(userKey: String, professionId: Int): Flow<Resource<Int>> {
        return repository.deleteSavedProfessions(userKey, professionId)
    }
}