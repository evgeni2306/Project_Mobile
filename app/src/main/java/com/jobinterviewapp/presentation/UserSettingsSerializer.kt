package com.jobinterviewapp.presentation

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserSettingsSerializer : Serializer<AuthSettings> {

    override val defaultValue: AuthSettings
        get() = AuthSettings()

    override suspend fun readFrom(input: InputStream): AuthSettings {
        return try {
            Json.decodeFromString(
                deserializer = AuthSettings.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AuthSettings, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AuthSettings.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}