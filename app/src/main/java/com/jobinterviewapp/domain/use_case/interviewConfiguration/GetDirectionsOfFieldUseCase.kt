package com.jobinterviewapp.domain.use_case.interviewConfiguration

import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.jobinterviewapp.domain.repository.InterviewConfigurationRepository
import com.weatherapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDirectionsOfFieldUseCase @Inject constructor(
    private val repository: InterviewConfigurationRepository
) {

    operator fun invoke(sphereId: Int): Flow<Resource<List<FieldOfActivityDto>>> {
        return repository.getDirectionsOfField(sphereId)
    }
}