package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.jobinterviewapp.data.remote.dto.InterviewPreviewDto
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.ProfessionDto
import kotlinx.coroutines.flow.Flow

interface InterviewConfigurationRepository {

    fun getFieldsOfActivity(): Flow<Resource<List<FieldOfActivityDto>>>

    fun getDirectionsOfField(fieldId: Int): Flow<Resource<List<FieldOfActivityDto>>>

    fun getTechnologiesOfDirection(directionId: Int): Flow<Resource<List<FieldOfActivityDto>>>

    fun getProfessionsOfTechnology(technologyId: Int): Flow<Resource<List<FieldOfActivityDto>>>

    fun getInterviewPreview(professionId: Int): Flow<Resource<InterviewPreviewDto>>

    fun getSavedProfessions(userKey: String): Flow<Resource<List<ProfessionDto>>>

    fun deleteSavedProfessions(userKey: String, professionId: Int): Flow<Resource<Int>>
}