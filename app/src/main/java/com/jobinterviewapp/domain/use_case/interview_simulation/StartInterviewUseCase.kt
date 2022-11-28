package com.jobinterviewapp.domain.use_case.interview_simulation

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.repository.InterviewSimulationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartInterviewUseCase @Inject constructor(
    private val repository: InterviewSimulationRepository
) {

    operator fun invoke(professionId: Int, userKey: String): Flow<Resource<Int>> {
        return repository.startInterview(professionId, userKey)
    }
}