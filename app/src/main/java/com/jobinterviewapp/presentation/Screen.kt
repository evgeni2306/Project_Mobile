package com.jobinterviewapp.presentation

import com.jobinterviewapp.R
import com.weatherapp.core.util.UiText

enum class Screen(val route: String, val screenName: UiText, val subRoutes: List<String>? = null) {
    RegistrationScreen("registration_screen", UiText.StringResource(R.string.registration_screen_name)),
    SignInScreen("sign_in_screen", UiText.StringResource(R.string.sign_in_screen_name))
}