package com.jobinterviewapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InterviewApplicationApi {
    @POST("registration")
    suspend fun register(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("login") login: String,
        @Query("password") password: String,
    ): Int

    companion object {
        const val BASE_URL = "https://server2306.site/"
    }
}