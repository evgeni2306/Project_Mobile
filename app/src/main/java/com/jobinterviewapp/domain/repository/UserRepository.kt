package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.domain.models.Credential
import com.weatherapp.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun registerUser(credential: Credential): Flow<Resource<String>>

    fun signInUser(login: String, password: String): Flow<Resource<String>>
}