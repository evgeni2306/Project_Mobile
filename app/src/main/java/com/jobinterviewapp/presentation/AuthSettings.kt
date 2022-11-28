package com.jobinterviewapp.presentation

import kotlinx.serialization.Serializable

@Serializable
data class AuthSettings(
    val authorized: Boolean = false,
    val userKey: String? = null,
)