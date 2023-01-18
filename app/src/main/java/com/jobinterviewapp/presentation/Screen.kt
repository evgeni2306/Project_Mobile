package com.jobinterviewapp.presentation

import com.jobinterviewapp.R
import com.jobinterviewapp.core.util.UiText

enum class Screen(val route: String, val screenName: UiText, val subRoutes: List<String>? = null) {
    RegistrationScreen("registration_screen", UiText.StringResource(R.string.registration_screen_name)),
    SignInScreen("sign_in_screen", UiText.StringResource(R.string.sign_in_screen_name)),
    SavedProfessionsScreen(
        "SavedProfessionsScreen",
        UiText.StringResource(R.string.saved_professions_screen),
        listOf(
            "directions_of_field_screen",
            "fields_of_activity_screen",
            "technologies_of_direction_screen",
            "professions_of_technology_screen",
            "interview_preview_screen",
            "interview_simulation_screen",
            "InterviewResultScreen",
        )
    ),
    DirectionsOfFieldScreen(
        "directions_of_field_screen",
        UiText.StringResource(R.string.directions_of_field_screen_name)
    ),
    TechnologiesOfDirectionScreen(
        "technologies_of_direction_screen",
        UiText.StringResource(R.string.technologies_of_direction_screen_name)
    ),
    ProfessionsOfTechnologyScreen(
        "professions_of_technology_screen",
        UiText.StringResource(R.string.professions_of_technology_screen_name)
    ),
    InterviewPreviewScreen(
        "interview_preview_screen",
        UiText.StringResource(R.string.interview_preview_screen_name)
    ),
    KnowledgeBaseScreen(
        "knowledge_base_screen",
        UiText.StringResource(R.string.knowledge_base_screen_name)
    ),
    InterviewSimulationScreen(
        "interview_simulation_screen",
        UiText.StringResource(R.string.interview_simulation_screen_name)
    ),
    FieldsOfActivityScreen(
        "fields_of_activity_screen",
        UiText.StringResource(R.string.fields_of_activity_screen_name),
    ),
    InterviewResultScreen(
        "InterviewResultScreen",
        UiText.StringResource(R.string.interview_simulation_screen_name),
    ),
    FavoriteTasksScreen(
        "FavoriteTasksScreen",
        UiText.StringResource(R.string.favorite_tasks_screen_name),
    ),
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