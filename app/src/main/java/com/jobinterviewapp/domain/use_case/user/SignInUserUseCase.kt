package com.jobinterviewapp.domain.use_case.user

import com.jobinterviewapp.domain.models.Credential
import com.jobinterviewapp.domain.repository.UserRepository
import com.weatherapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUserUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(login: String, password: String): Flow<Resource<String>> {
        return repository.signInUser(login, password)
    }
}