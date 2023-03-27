package tech.mosaleh.together.presentation.screens.utils

sealed class Screens(val route: String) {
    object Home : Screens(route = "Home")
    object CaseDetails : Screens(route = "case_details")
    object Login : Screens(route = "login")
    object Registration : Screens(route = "registration")
}
