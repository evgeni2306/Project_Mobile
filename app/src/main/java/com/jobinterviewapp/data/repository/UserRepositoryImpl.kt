package com.jobinterviewapp.data.repository

import com.google.gson.Gson
import com.jobinterviewapp.R
import com.jobinterviewapp.data.remote.InterviewApplicationApi
import com.jobinterviewapp.data.remote.dto.ErrorDto
import com.jobinterviewapp.domain.models.Credential
import com.jobinterviewapp.domain.repository.UserRepository
import com.weatherapp.core.util.Resource
import com.weatherapp.core.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val api: InterviewApplicationApi
): UserRepository {
    override fun registerUser(credential: Credential): Flow<Resource<String>> = flow {
        try {
            val data = api.register(
                name = credential.name,
                surname = credential.surname,
                login = credential.login,
                password = credential.password,
            )

            emit(Resource.Success(data = data.key))
        }
        catch(e: HttpException) {
            val errorMessage = if(e.localizedMessage.isNullOrEmpty()) {
                UiText.StringResource(R.string.unknown_exception)
            }
            else {
                val errorBody = e.response()?.errorBody()
                if (errorBody == null) {
                    UiText.DynamicString(e.localizedMessage!!)
                }
                else {
                    val errorMessage = Gson().fromJson(errorBody.charStream(), ErrorDto::class.java).message
                    UiText.DynamicString(errorMessage)
                }
            }
            emit(Resource.Error(errorMessage))
        }
        catch(e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.io_exception)))
        }
    }

    override fun signInUser(login: String, password: String): Flow<Resource<String>> = flow {
        try {
            val data = api.signIn(
                login = login,
                password = password,
            )

            emit(Resource.Success(data = data.key))
        }
        catch(e: HttpException) {
            val errorMessage = if(e.localizedMessage.isNullOrEmpty()) {
                UiText.StringResource(R.string.unknown_exception)
            }
            else {
                val errorBody = e.response()?.errorBody()
                if (errorBody == null) {
                    UiText.DynamicString(e.localizedMessage!!)
                }
                else {
                    val errorMessage = Gson().fromJson(errorBody.charStream(), ErrorDto::class.java).message
                    UiText.DynamicString(errorMessage)
                }
            }
            emit(Resource.Error(errorMessage))
        }
        catch(e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.io_exception)))
        }
    }
}