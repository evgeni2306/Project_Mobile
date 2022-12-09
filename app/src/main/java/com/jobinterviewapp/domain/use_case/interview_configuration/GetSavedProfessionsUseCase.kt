package com.jobinterviewapp.domain.use_case.interview_configuration

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.ProfessionDto
import com.jobinterviewapp.domain.repository.InterviewConfigurationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedProfessionsUseCase @Inject constructor(
    private val repository: InterviewConfigurationRepository
) {

    operator fun invoke(userKey: String): Flow<Resource<List<ProfessionDto>>> {
        return repository.getSavedProfessions(userKey)
    }
}