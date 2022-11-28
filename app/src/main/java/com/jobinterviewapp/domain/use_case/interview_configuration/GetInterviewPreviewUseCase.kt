package com.jobinterviewapp.domain.use_case.interview_configuration

import com.jobinterviewapp.data.remote.dto.InterviewPreviewDto
import com.jobinterviewapp.domain.repository.InterviewConfigurationRepository
import com.jobinterviewapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInterviewPreviewUseCase @Inject constructor(
    private val repository: InterviewConfigurationRepository
) {

    operator fun invoke(professionId: Int): Flow<Resource<InterviewPreviewDto>> {
        return repository.getInterviewPreview(professionId)
    }
}