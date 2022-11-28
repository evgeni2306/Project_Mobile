package com.jobinterviewapp.domain.use_case.interview_simulation

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.repository.InterviewSimulationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostInterviewTaskAnswerUseCase @Inject constructor(
    private val repository: InterviewSimulationRepository
) {

    operator fun invoke(taskId: Int, userKey: String, answer: Boolean): Flow<Resource<Int>> {
        return repository.postInterviewTaskAnswer(taskId, userKey, answer)
    }
}