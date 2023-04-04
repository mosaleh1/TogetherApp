package tech.mosaleh.together

import SetUpNavGraph
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import tech.mosaleh.together.domain.model.CaseType
import tech.mosaleh.together.presentation.screens.add_case.LocationPickerScreen
import tech.mosaleh.together.presentation.screens.home.HomeScreen
import tech.mosaleh.together.presentation.screens.login.LoginScreen
import tech.mosaleh.together.presentation.screens.registration.RegistrationScreen
import tech.mosaleh.together.ui.theme.TogetherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
            ),
            0
        )
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            Intent(applicationContext, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                startService(this)
            }
        }
        setContent {
            TogetherTheme {
                val navController = rememberNavController()
                SetUpNavGraph(navController = navController)
            }
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM Token", "Fetching FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                // Get the token and use it as desired
                val token = task.result
                Log.d("FCM Token", token ?: "Token is null")
            }
        }
    }

}









