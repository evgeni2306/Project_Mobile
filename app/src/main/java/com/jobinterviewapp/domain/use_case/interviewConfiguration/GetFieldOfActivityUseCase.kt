package com.jobinterviewapp.domain.use_case.interviewConfiguration

import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.jobinterviewapp.domain.repository.InterviewConfigurationRepository
import com.weatherapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFieldOfActivityUseCase @Inject constructor(
    private val repository: InterviewConfigurationRepository
) {

    operator fun invoke(): Flow<Resource<FieldOfActivityDto>> {
        return repository.getFieldOfActivity()
    }
}