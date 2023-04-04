import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import tech.mosaleh.together.presentation.screens.utils.Screens

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    val screens = remember {
        listOf(
            Screen(
                route = Screens.Home.route,
                content = { HomeScreen(navController) }
            ),
            Screen(
                route = Screens.Login.route,
                content = { LoginScreen(navController = navController) }
            ),
            Screen(
                route = Screens.Registration.route,
                content = { RegistrationScreen(navController = navController) }
            ),
            Screen(
                route = Screens.CaseDetails.route,
                content = {
                    val case =
                        navController.previousBackStackEntry?.savedStateHandle?.get<Case>("case")
                    case?.let {
                        CaseDetailsScreen(navController, case)
                    }

                }
            ),
            Screen(
                route = Screens.AddCase.route,
                content = { AddCaseScreen(navController) }
            ),
            Screen(
                route = Screens.LocationPicker.route,
                content = { LocationPickerScreen(navController = navController) }
            ),
        )
    }

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        screens.forEach { screen ->
            composable(route = screen.route) {
                screen.content()
            }
        }
    }
}

data class Screen(val route: String, val content: @Composable () -> Unit)