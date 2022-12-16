package com.jobinterviewapp.domain.use_case.interview_simulation

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.InterviewResultDto
import com.jobinterviewapp.domain.repository.InterviewSimulationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterviewResultUseCase @Inject constructor(
    private val repository: InterviewSimulationRepository
) {

    operator fun invoke(userKey: String, interviewId: Int): Flow<Resource<InterviewResultDto>> {
        return repository.getInterviewResult(userKey, interviewId)
    }
}