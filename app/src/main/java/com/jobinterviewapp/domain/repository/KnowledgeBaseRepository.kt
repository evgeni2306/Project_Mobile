package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.ProfessionDto
import com.jobinterviewapp.domain.models.Task
import kotlinx.coroutines.flow.Flow

interface KnowledgeBaseRepository {
    fun getProfessionList(userKey: String): Flow<Resource<List<ProfessionDto>>>

    fun getProfessionTaskList(userKey: String, profId: Int): Flow<Resource<List<Task>>>
}