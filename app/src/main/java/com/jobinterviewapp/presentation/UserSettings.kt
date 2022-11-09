package com.jobinterviewapp.presentation

import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val authorized: Boolean = false,
)