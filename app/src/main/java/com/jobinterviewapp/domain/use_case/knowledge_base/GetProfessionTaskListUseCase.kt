package com.jobinterviewapp.domain.use_case.knowledge_base

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.models.Task
import com.jobinterviewapp.domain.repository.KnowledgeBaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfessionTaskListUseCase @Inject constructor(
    private val repository: KnowledgeBaseRepository
) {
    operator fun invoke(userKey: String, profId: Int): Flow<Resource<List<Task>>> {
        return repository.getProfessionTaskList(userKey, profId)
    }
}