package tech.mosaleh.together

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.mosaleh.together.domain.model.CaseType
import tech.mosaleh.together.presentation.screens.home.HomeScreen
import tech.mosaleh.together.presentation.screens.login.LoginScreen
import tech.mosaleh.together.presentation.screens.registration.RegistrationScreen
import tech.mosaleh.together.presentation.screens.utils.SetUpNavGraph
import tech.mosaleh.together.ui.theme.TogetherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TogetherTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
        }
    }

}









