package com.jobinterviewapp.data.repository

import com.google.gson.Gson
import com.jobinterviewapp.R
import com.jobinterviewapp.data.remote.InterviewServiceApi
import com.jobinterviewapp.data.remote.dto.ErrorDto
import com.jobinterviewapp.domain.models.Credential
import com.jobinterviewapp.domain.repository.UserRepository
import com.jobinterviewapp.core.util.Resource
import com.jobinterviewapp.core.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val api: InterviewServiceApi
): UserRepository {
    override fun registerUser(credential: Credential): Flow<Resource<String>> = flow {
        emit(safeApiCall {
            api.register(
                name = credential.name,
                surname = credential.surname,
                login = credential.login,
                password = credential.password,
            ).key
        })
    }.flowOn(Dispatchers.IO)

    override fun signInUser(login: String, password: String): Flow<Resource<String>> = flow {
        emit(safeApiCall {
            api.signIn(
                login = login,
                password = password,
            ).key
        })
    }.flowOn(Dispatchers.IO)

    override fun addTaskToFavorites(userKey: String, taskId: Int): Flow<Resource<Int>> = flow {
        emit(safeApiCall {
            api.addTaskToFavorites(
                userKey = userKey,
                taskId = taskId,
            )
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