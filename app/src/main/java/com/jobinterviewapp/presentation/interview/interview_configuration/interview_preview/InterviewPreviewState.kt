package com.jobinterviewapp.presentation.interview.interview_configuration.interview_preview

import com.jobinterviewapp.core.util.UiText

data class InterviewPreviewState(
    val previewName: String? = null,
    val professionId: Int = 0,
    val questionsCount: Int? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null,
)