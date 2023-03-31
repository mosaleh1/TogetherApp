package tech.mosaleh.together.presentation.screens.add_case

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import tech.mosaleh.together.domain.model.CaseAddress
import tech.mosaleh.together.domain.use_cases.GetCompleteAddressUseCase
import tech.mosaleh.together.domain.utils.ADDRESS_EXTRA

@Composable
fun LocationPickerScreen(navController: NavHostController) {
    val cairo = LatLng(30.0444, 31.2357)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cairo, 15f)
    }
    var latLng by remember {
        mutableStateOf<LatLng?>(null)
    }
    val context = LocalContext.current
    var showButton by remember { mutableStateOf(false) }
    Box(Modifier.fillMaxSize()) {
        GoogleMap(Modifier.fillMaxSize(),
            cameraPositionState,
            onMapLongClick = {
                latLng = it
            }) {
            latLng?.let {
                showButton = true
                Marker(
                    state = MarkerState(position = it),
                    title = "Selected Area",
                    snippet = "Case Location"
                )
            }
        }
        if (showButton) {
            Button(
                onClick = {
                    // Button clicked
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(ADDRESS_EXTRA, latLng)
                    navController.popBackStack()

                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = "Confirm Selected")
            }
        }
    }
}