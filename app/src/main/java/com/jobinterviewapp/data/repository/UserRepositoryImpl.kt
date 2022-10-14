package com.jobinterviewapp.data.repository

import android.util.Log
import com.jobinterviewapp.R
import com.jobinterviewapp.data.remote.InterviewApplicationApi
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
    override fun registerUser(credential: Credential): Flow<Resource<Int>> = flow {
        try {
            val responseResult = api.register(
                name = credential.name,
                surname = credential.surname,
                login = credential.login,
                password = credential.password,
            )
            if(responseResult.isSuccessful) {
                emit(Resource.Success(responseResult.body()!!))
            }
            else {
                emit(Resource.Error(UiText.DynamicString(responseResult.errorBody().toString())))
            }
        }
        catch(e: HttpException) {
            if(e.localizedMessage.isNullOrEmpty()) {
                emit(Resource.Error(UiText.StringResource(R.string.unknown_exception)))
            }
            else {
                val errorBodyMsg = e.response()?.errorBody()
                if (errorBodyMsg != null) {
                    emit(Resource.Error(UiText.DynamicString(errorBodyMsg.toString())))
                }
                else {
                    emit(Resource.Error(UiText.DynamicString(e.localizedMessage!!)))
                }
            }
        }
        catch(e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.io_exception)))
        }
    }
}