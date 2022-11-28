package com.jobinterviewapp.di

import android.content.Context
import androidx.datastore.dataStore
import com.jobinterviewapp.data.remote.InterviewServiceApi
import com.jobinterviewapp.presentation.Constants
import com.jobinterviewapp.presentation.UserSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInterviewApplicationApi(): InterviewServiceApi {
        return Retrofit.Builder()
            .baseUrl(InterviewServiceApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    private val Context.dataStore by dataStore(Constants.USER_SETTINGS_FILE_NAME, UserSettingsSerializer)

    @Singleton
    class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
        private val authSettingsDataStore = appContext.dataStore
        suspend fun setAuthSettings(authorized: Boolean, userKey: String) {
            authSettingsDataStore.updateData {
                it.copy(authorized = authorized, userKey = userKey)
            }
        }

        suspend fun setUserKey(userKey: String) {
            authSettingsDataStore.updateData {
                it.copy(userKey = userKey)
            }
        }

        suspend fun setAuthStatus(authorized: Boolean) {
            authSettingsDataStore.updateData {
                it.copy(authorized = authorized)
            }
        }

        val authSettings = authSettingsDataStore.data
    }
}