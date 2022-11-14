package com.jobinterviewapp.presentation.interview.interview_preview

data class InterviewPreviewState(
    val previewName: String? = null,
    val questionsCount: Int? = null,
    val isLoading: Boolean = false,
)