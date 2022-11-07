package com.jobinterviewapp.data.repository

import com.google.gson.Gson
import com.jobinterviewapp.R
import com.jobinterviewapp.data.remote.InterviewApplicationApi
import com.jobinterviewapp.data.remote.dto.ErrorDto
import com.jobinterviewapp.domain.repository.InterviewConfigurationRepository
import com.weatherapp.core.util.Resource
import com.weatherapp.core.util.UiText
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InterviewConfigurationRepositoryImpl @Inject constructor(
    private val api: InterviewApplicationApi,
): InterviewConfigurationRepository {

    override fun getFieldOfActivity() = flow {
        try {
            val data = api.getFieldOfActivity()
            emit(Resource.Success(data = data))
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

    override fun getDirectionsOfField(sphereId: Int) = flow {
        try {
            val data = api.getDirectionsOfField(sphereId)
            emit(Resource.Success(data = data))
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