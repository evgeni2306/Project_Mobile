package com.jobinterviewapp.data.repository

import com.google.gson.Gson
import com.jobinterviewapp.R
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.core.util.UiText
import com.jobinterviewapp.data.remote.InterviewServiceApi
import com.jobinterviewapp.data.remote.dto.ErrorDto
import com.jobinterviewapp.data.remote.dto.TaskDto
import com.jobinterviewapp.domain.repository.InterviewSimulationRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InterviewSimulationRepositoryImpl @Inject constructor(
    private val api: InterviewServiceApi,
): InterviewSimulationRepository {

    override fun startInterview(professionId: Int, userKey: String): Flow<Resource<Int>> {
        return flow {
            emit(safeApiCall { api.startInterview(professionId, userKey).interviewId })
        }.flowOn(Dispatchers.IO)
    }

    override fun postInterviewTaskAnswer(taskId: Int, userKey: String, answer: Boolean,): Flow<Resource<Int>> {
        return flow {
            emit(safeApiCall { api.postInterviewTaskAnswer(
                taskId = taskId,
                userKey = userKey,
                answer = answer
            ) })
        }.flowOn(Dispatchers.IO)
    }

    override fun getInterviewTasks(taskCount: Int, userKey: String, interviewId: Int): Flow<Resource<List<TaskDto>>> = flow {
        emit(safeApiCall {
            coroutineScope {
                val deferredTaskList = mutableListOf<Deferred<TaskDto>>()
                for(i in 0 until taskCount) {
                    val deferredTask = async(Dispatchers.IO) {
                        api.getInterviewTask(interviewId, userKey)
                    }
                    deferredTaskList.add(deferredTask)
                }
                deferredTaskList.awaitAll()
            }
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