package com.jobinterviewapp.domain.use_case.interview_configuration

import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.jobinterviewapp.domain.repository.InterviewConfigurationRepository
import com.jobinterviewapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTechnologiesOfDirectionUseCase @Inject constructor(
    private val repository: InterviewConfigurationRepository
) {

    operator fun invoke(directionId: Int): Flow<Resource<List<FieldOfActivityDto>>> {
        return repository.getTechnologiesOfDirection(directionId)
    }
}