package com.jobinterviewapp.data.remote

import com.jobinterviewapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InterviewServiceApi {
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
        @Path("directionId") directionId: Int,
    ): List<FieldOfActivityDto>

    @GET("interview/new/sphere/direction/technology={technologyId}")
    suspend fun getProfessionsOfTechnology(
        @Path("technologyId") technologyId: Int,
    ): List<FieldOfActivityDto>

    @GET("interview/new/sphere/direction/technology/profession={professionId}")
    suspend fun getInterviewPreview(
        @Path("professionId") professionId: Int,
    ): InterviewPreviewDto

    @POST("interview/start")
    suspend fun startInterview(
        @Query("profId") professionId: Int,
        @Query("authKey") userKey: String,
    ): StartInterviewDto

    @POST("interview/question")
    suspend fun getInterviewTask(
        @Query("interviewId") interviewId: Int,
        @Query("authKey") userKey: String,
    ): TaskDto

    @POST("interview/question/answer")
    suspend fun postInterviewTaskAnswer(
        @Query("taskId") taskId: Int,
        @Query("authKey") userKey: String,
        @Query("answer") answer: Boolean,
    ): Int

    companion object {
        const val BASE_URL = "http://server2306.site/"
    }
}