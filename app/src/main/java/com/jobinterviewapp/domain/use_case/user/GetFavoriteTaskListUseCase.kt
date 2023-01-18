package com.jobinterviewapp.domain.use_case.user

import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.data.remote.dto.FavoriteTaskDto
import com.jobinterviewapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteTaskListUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(userKey: String): Flow<Resource<List<FavoriteTaskDto>>> {
        return repository.getFavoriteTaskList(userKey)
    }
}