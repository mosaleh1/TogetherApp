package tech.mosaleh.together.presentation.screens.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.presentation.screens.add_case.AddCaseScreen
import tech.mosaleh.together.presentation.screens.add_case.LocationPickerScreen
import tech.mosaleh.together.presentation.screens.case_details.CaseDetailsScreen
import tech.mosaleh.together.presentation.screens.home.HomeScreen
import tech.mosaleh.together.presentation.screens.login.LoginScreen
import tech.mosaleh.together.presentation.screens.registration.RegistrationScreen


@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(
            route = Screens.Home.route,
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screens.Login.route,
        ) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screens.Registration.route,
        ) {
            RegistrationScreen(navController = navController)
        }

        composable(
            route = Screens.CaseDetails.route,
        ) {
            val case = navController.previousBackStackEntry?.savedStateHandle?.get<Case>("case")
            case?.let {
                CaseDetailsScreen(case)
            }
        }
        composable(
            route = Screens.AddCase.route,
        ) {
            AddCaseScreen(navController)
        }
        composable(
            route = Screens.LocationPicker.route,
        ) {
            LocationPickerScreen(navController = navController)
        }
    }
}







