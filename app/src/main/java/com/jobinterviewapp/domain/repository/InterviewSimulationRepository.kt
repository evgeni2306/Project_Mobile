package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.TaskDto
import kotlinx.coroutines.flow.Flow

interface InterviewSimulationRepository {
    fun startInterview(professionId: Int, userKey: String): Flow<Resource<Int>>

    fun getInterviewTasks(taskCount: Int, userKey: String, interviewId: Int): Flow<Resource<List<TaskDto>>>

    fun postInterviewTaskAnswer(taskId: Int, userKey: String, answer: Boolean,): Flow<Resource<Int>>
}