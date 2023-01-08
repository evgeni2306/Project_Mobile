package com.jobinterviewapp.domain.use_case.knowledge_base

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.ProfessionDto
import com.jobinterviewapp.domain.repository.KnowledgeBaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfessionListUseCase @Inject constructor(
    private val repository: KnowledgeBaseRepository
) {
    operator fun invoke(userKey: String): Flow<Resource<List<ProfessionDto>>> {
        return repository.getProfessionList(userKey)
    }
}