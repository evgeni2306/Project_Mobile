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
    ): Response<InterviewTaskDto>

    @POST("interview/question/answer")
    suspend fun postInterviewTaskAnswer(
        @Query("taskId") taskId: Int,
        @Query("authKey") userKey: String,
        @Query("answer") answer: Int,
    ): Int

    @POST("interview/templates")
    suspend fun getSavedProfessions(
        @Query("authKey") userKey: String,
    ): List<SavedProfessionDto>

    @POST("interview/templates/delete")
    suspend fun deleteSavedProfessions(
        @Query("authKey") userKey: String,
        @Query("templateId") professionId: Int,
    ): Int

    @POST("interview/results")
    suspend fun getInterviewResult(
        @Query("authKey") userKey: String,
        @Query("interviewId") interviewId: Int,
    ): InterviewResultDto

    @POST("question/favorite/add")
    suspend fun addTaskToFavorites(
        @Query("authKey") userKey: String,
        @Query("questionId") taskId: Int,
    ): Int

    @POST("question/favorite/delete")
    suspend fun deleteTaskFromFavorites(
        @Query("authKey") userKey: String,
        @Query("favoriteId") favoriteId: Int,
    ): Int

    @POST("knowledgebase/professions")
    suspend fun getProfessionList(
        @Query("authKey") userKey: String,
    ): List<ProfessionDto>

    @POST("knowledgebase/professions/questions")
    suspend fun getProfessionTaskList(
        @Query("authKey") userKey: String,
        @Query("profId") profId: Int,
    ): List<TaskDto>

    @POST("question/favorite/list")
    suspend fun getFavoriteTaskList(
        @Query("authKey") userKey: String,
    ): List<FavoriteTaskDto>

    companion object {
        const val BASE_URL = "http://server2306.site/"
    }
}