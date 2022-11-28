package com.jobinterviewapp.domain.use_case.interview_simulation

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.TaskDto
import com.jobinterviewapp.domain.repository.InterviewSimulationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterviewTaskUseCase @Inject constructor(
    private val repository: InterviewSimulationRepository
) {

    operator fun invoke(taskCount: Int, userKey: String, interviewId: Int): Flow<Resource<List<TaskDto>>> {
        return repository.getInterviewTasks(taskCount, userKey, interviewId)
    }
}
