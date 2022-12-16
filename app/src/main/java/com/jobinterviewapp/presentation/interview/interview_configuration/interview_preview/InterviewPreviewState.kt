package com.jobinterviewapp.presentation.interview.interview_configuration.interview_preview

data class InterviewPreviewState(
    val previewName: String? = null,
    val professionId: Int = 0,
    val questionsCount: Int? = null,
    val isLoading: Boolean = false,
)