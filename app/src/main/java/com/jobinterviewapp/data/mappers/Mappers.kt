package com.jobinterviewapp.data.mappers

import com.jobinterviewapp.data.remote.dto.InterviewResultDto
import com.jobinterviewapp.data.remote.dto.InterviewTaskDto
import com.jobinterviewapp.data.remote.dto.TaskDto
import com.jobinterviewapp.domain.models.InterviewResult
import com.jobinterviewapp.domain.models.InterviewTask
import com.jobinterviewapp.domain.models.Task

fun TaskDto.toTask(): Task {
    return Task(
        question = question,
        questionId = questionId,
        answer = answer,
        category = category,
        favoriteId = if(favoriteId == 0) null else favoriteId,
        isFavorite = isFavorite == 1,
    )
}

fun InterviewResultDto.toInterviewResult(): InterviewResult {
    return InterviewResult(
        countRight = countRight,
        countWrong = countWrong,
        wrongQuestions = wrongQuestions.map { it.toTask() }
    )
}

fun InterviewTaskDto.toInterviewTask(): InterviewTask {
    return InterviewTask(
        taskId = taskId,
        question = question,
        answer = answer,
        category = category,
        questionId = questionId,
        isFavorite = isFavorite == 1,
        favoriteId = if(favoriteId == 0) null else favoriteId,
    )
}