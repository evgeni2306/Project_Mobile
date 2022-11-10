package com.jobinterviewapp.presentation

import com.jobinterviewapp.R
import com.weatherapp.core.util.UiText

enum class Screen(val route: String, val screenName: UiText, val subRoutes: List<String>? = null) {
    RegistrationScreen("registration_screen", UiText.StringResource(R.string.registration_screen_name)),
    SignInScreen("sign_in_screen", UiText.StringResource(R.string.sign_in_screen_name)),
    FieldsOfActivityScreen(
        "fields_of_activity_screen",
        UiText.StringResource(R.string.home_screen_name),
        listOf(
            "directions_of_field_screen",
            "technologies_of_direction_screen",
            "professions_of_technology_screen",
        )
    ),
    DirectionsOfFieldScreen("directions_of_field_screen", UiText.StringResource(R.string.home_screen_name)),
    TechnologiesOfDirectionScreen("technologies_of_direction_screen", UiText.StringResource(R.string.home_screen_name)),
    ProfessionsOfTechnologyScreen("professions_of_technology_screen", UiText.StringResource(R.string.home_screen_name)),
    KnowledgeBaseScreen("knowledge_base_screen", UiText.StringResource(R.string.knowledge_base_screen)),
    ;

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}