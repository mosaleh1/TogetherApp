package tech.mosaleh.together.presentation.screens.case_details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import org.w3c.dom.Text
import tech.mosaleh.together.R
import tech.mosaleh.together.domain.model.Case

@Composable
fun CaseDetailsScreen(case: Case) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Case Details") },
                navigationIcon = {
                    IconButton(onClick = { /* Navigate back */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Log.d("_", "CaseDetailsScreen: $it")
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = case.imageUrl,
                        builder = {
                            crossfade(true)
                            placeholder(R.drawable.empty)
                        }
                    ),
                    contentDescription = "Case Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = case.caseName, style = typography.h5)

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Type: ${case.type}", style = typography.body1)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Location: ${case.caseAddress.addressStr}", style = typography.body1)

                Spacer(modifier = Modifier.height(8.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Description:", style = typography.subtitle1)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = case.caseDescription, style = typography.body1)
            }
        })
}
