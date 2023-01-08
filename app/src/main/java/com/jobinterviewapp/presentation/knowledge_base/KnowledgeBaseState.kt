package com.jobinterviewapp.presentation.knowledge_base

import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.remote.dto.ProfessionDto
import com.jobinterviewapp.domain.models.Task

data class KnowledgeBaseState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val professionList: List<ProfessionDto> = emptyList(),
    val userKey: String? = null,
    val currentProfession: ProfessionDto? = null,
    val currentProfessionTaskList: List<Task> = emptyList(),
    val searchValue: String = "",
)