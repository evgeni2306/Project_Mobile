package com.jobinterviewapp.domain.use_case.user

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTaskFromFavoritesUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(userKey: String, favoriteId: Int): Flow<Resource<Int>> {
        return repository.deleteTaskFromFavorites(userKey, favoriteId)
    }
}