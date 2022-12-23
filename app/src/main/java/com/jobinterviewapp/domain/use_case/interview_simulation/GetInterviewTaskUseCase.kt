package com.jobinterviewapp.domain.use_case.interview_simulation

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.models.InterviewTask
import com.jobinterviewapp.domain.repository.InterviewSimulationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterviewTaskUseCase @Inject constructor(
    private val repository: InterviewSimulationRepository
) {

    operator fun invoke(userKey: String, interviewId: Int): Flow<Resource<InterviewTask?>> {
        return repository.getInterviewTask(userKey, interviewId)
    }
}
