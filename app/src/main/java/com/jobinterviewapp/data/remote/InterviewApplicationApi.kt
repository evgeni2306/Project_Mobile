package com.jobinterviewapp.data.remote

import com.jobinterviewapp.data.remote.dto.AuthResponseDto
import com.jobinterviewapp.data.remote.dto.FieldOfActivityDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InterviewApplicationApi {
    @POST("registration")
    suspend fun register(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("login") login: String,
        @Query("password") password: String,
    ): AuthResponseDto

    @POST("login")
    suspend fun signIn(
        @Query("login") login: String,
        @Query("password") password: String,
    ): AuthResponseDto

    @GET("interview/new")
    suspend fun getFieldOfActivity(
    ): List<FieldOfActivityDto>

    @GET("interview/new/sphere={fieldId}")
    suspend fun getDirectionsOfField(
        @Path("fieldId") fieldId: Int,
    ): List<FieldOfActivityDto>

    @GET("interview/new/sphere/direction={directionId}")
    suspend fun getTechnologiesOfDirection(
        @Query("directionId") directionId: Int,
    ): List<FieldOfActivityDto>

    @GET("interview/new/sphere/direction")
    suspend fun getProfessionsOfTechnology(
        @Query("technology") technologyId: Int,
    ): List<FieldOfActivityDto>

    companion object {
        const val BASE_URL = "http://server2306.site/"
    }
}