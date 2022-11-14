package com.jobinterviewapp.di

import com.jobinterviewapp.data.remote.InterviewServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInterviewApplicationApi(): InterviewServiceApi {
        return Retrofit.Builder()
            .baseUrl(InterviewServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}