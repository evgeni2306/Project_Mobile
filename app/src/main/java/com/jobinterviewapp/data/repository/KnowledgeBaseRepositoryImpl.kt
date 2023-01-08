package com.jobinterviewapp.data.repository

import com.google.gson.Gson
import com.jobinterviewapp.R
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.mappers.toTask
import com.jobinterviewapp.data.remote.InterviewServiceApi
import com.jobinterviewapp.data.remote.dto.ErrorDto
import com.jobinterviewapp.data.remote.dto.ProfessionDto
import com.jobinterviewapp.domain.models.Task
import com.jobinterviewapp.domain.repository.KnowledgeBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class KnowledgeBaseRepositoryImpl @Inject constructor(
    private val api: InterviewServiceApi,
): KnowledgeBaseRepository {
    override fun getProfessionList(userKey: String): Flow<Resource<List<ProfessionDto>>> = flow {
        emit(safeApiCall {
            api.getProfessionList(
                userKey = userKey
            )
        })
    }.flowOn(Dispatchers.IO)

    override fun getProfessionTaskList(userKey: String, profId: Int): Flow<Resource<List<Task>>> = flow {
        emit(safeApiCall {
            api.getProfessionTaskList(
                userKey = userKey,
                profId = profId,
            ).map { it.toTask() }
        })
    }.flowOn(Dispatchers.IO)

    private inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
        return try {
            val data = apiCall()
            Resource.Success(data = data)
        } catch(throwable: Throwable) {
            Resource.Error(handleThrowableException(throwable))
        }
    }

    private fun handleThrowableException(throwable: Throwable): UiText {
        return when(throwable) {
            is HttpException -> {
                if(throwable.localizedMessage.isNullOrEmpty()) {
                    UiText.StringResource(R.string.unknown_exception)
                }
                else {
                    val errorBody = throwable.response()?.errorBody()
                    if(errorBody == null) {
                        UiText.DynamicString(throwable.localizedMessage!!)
                    } else {
                        UiText.DynamicString(Gson().fromJson(errorBody.charStream(), ErrorDto::class.java).message)
                    }
                }
            }
            is IOException -> {
                UiText.StringResource(R.string.io_exception)
            }
            else -> UiText.StringResource(R.string.unknown_exception)
        }
    }
}