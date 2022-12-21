package com.jobinterviewapp.domain.use_case.user

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddTaskToFavoritesUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(userKey: String, taskId: Int): Flow<Resource<Int>> {
        return repository.addTaskToFavorites(userKey, taskId)
    }
}