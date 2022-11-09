package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import com.weatherapp.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface InterviewConfigurationRepository {

    fun getFieldsOfActivity(): Flow<Resource<List<FieldOfActivityDto>>>

    fun getDirectionsOfField(fieldId: Int): Flow<Resource<List<FieldOfActivityDto>>>

    fun getTechnologiesOfDirection(directionId: Int): Flow<Resource<List<FieldOfActivityDto>>>

    fun getProfessionsOfTechnology(technologyId: Int): Flow<Resource<List<FieldOfActivityDto>>>
}