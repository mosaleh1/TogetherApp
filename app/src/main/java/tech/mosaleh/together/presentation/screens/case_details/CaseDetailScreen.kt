package tech.mosaleh.together.presentation.screens.case_details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.LatLng
import tech.mosaleh.together.R
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.presentation.screens.utils.Screens

@Composable
fun CaseDetailsScreen(navController: NavHostController, case: Case) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Case Details") }, navigationIcon = {
            IconButton(onClick = {
                navController.navigate(
                    route = Screens.Home.route
                )
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }, content = {
        Log.d("T", "CaseDetailsScreen: $it")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberImagePainter(data = case.imageUrl, builder = {
                    crossfade(true)
                }),
                contentDescription = "Case Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = case.caseName,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = case.caseDescription,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location Icon",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(CenterVertically)
                            .fillMaxHeight()
                    )
                    Text(
                        text = case.caseAddress.addressStr,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_category),
                        contentDescription = "Category Icon",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = case.type.name,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_status),
                        contentDescription = "Status Icon",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = case.status.name,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_needs),
                        contentDescription = "Needs Icon",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = case.caseNeeds.name,
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            val context = LocalContext.current
            val mapIntentLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    // Handle the result of the map intent here
                }

            Button(modifier =
            Modifier.align(CenterHorizontally),
                onClick = {
                    val uri =
                        Uri.parse("google.navigation:q=${case.caseAddress.lat},${case.caseAddress.lng}&mode=d")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    mapIntentLauncher.launch(intent)
                }) {
                Text(text = "Give support")
            }
        }
    })
}


fun MapScreen(lat: Double, lng: Double, context: Context) {

}