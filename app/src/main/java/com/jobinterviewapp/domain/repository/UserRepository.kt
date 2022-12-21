package com.jobinterviewapp.domain.repository

import com.jobinterviewapp.domain.models.Credential
import com.jobinterviewapp.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun registerUser(credential: Credential): Flow<Resource<String>>

    fun signInUser(login: String, password: String): Flow<Resource<String>>

    fun addTaskToFavorites(userKey: String, taskId: Int): Flow<Resource<Int>>
}