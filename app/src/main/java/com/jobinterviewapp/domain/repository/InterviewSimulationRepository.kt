package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.TaskDto
import kotlinx.coroutines.flow.Flow

interface InterviewSimulationRepository {
    fun startInterview(professionId: Int, userKey: String): Flow<Resource<Int>>

    fun getInterviewTask(userKey: String, interviewId: Int): Flow<Resource<TaskDto>>

    fun postInterviewTaskAnswer(taskId: Int, userKey: String, answer: Boolean,): Flow<Resource<Int>>
}