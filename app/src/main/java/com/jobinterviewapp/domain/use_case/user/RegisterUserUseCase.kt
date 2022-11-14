package com.jobinterviewapp.domain.use_case.user

import com.jobinterviewapp.domain.models.Credential
import com.jobinterviewapp.domain.repository.UserRepository
import com.jobinterviewapp.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(credential: Credential): Flow<Resource<String>> {
        return repository.registerUser(credential)
    }
}