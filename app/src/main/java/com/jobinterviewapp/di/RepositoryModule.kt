package com.jobinterviewapp.di

import com.jobinterviewapp.data.repository.UserRepositoryImpl
import com.jobinterviewapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        weatherRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}