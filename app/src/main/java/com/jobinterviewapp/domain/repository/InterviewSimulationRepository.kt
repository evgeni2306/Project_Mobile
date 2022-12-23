package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.models.InterviewResult
import com.jobinterviewapp.domain.models.InterviewTask
import kotlinx.coroutines.flow.Flow

interface InterviewSimulationRepository {
    fun startInterview(professionId: Int, userKey: String): Flow<Resource<Int>>

    fun getInterviewTask(userKey: String, interviewId: Int): Flow<Resource<InterviewTask?>>

    fun postInterviewTaskAnswer(taskId: Int, userKey: String, answer: Boolean,): Flow<Resource<Int>>

    fun getInterviewResult(userKey: String, interviewId: Int): Flow<Resource<InterviewResult>>
}