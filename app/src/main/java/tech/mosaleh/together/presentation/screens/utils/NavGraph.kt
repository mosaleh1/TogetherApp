package tech.mosaleh.together.presentation.screens.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import tech.mosaleh.together.presentation.screens.add_edit_case.AddEditCaseScreen
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
            AddEditCaseScreen(navController)
        }
    }
}







