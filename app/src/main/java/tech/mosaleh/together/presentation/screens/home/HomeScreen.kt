package tech.mosaleh.together.presentation.screens.home

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.presentation.screens.home.components.CaseCard
import tech.mosaleh.together.presentation.screens.utils.Screens


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), navController: NavHostController
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            navController.navigate(
                route = Screens.Login.route
            )
        }

        viewModel.onEvent(HomeEvents.GetCases)
    }
    val state by rememberUpdatedState(newValue = viewModel.state)
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(
                route = Screens.AddCase.route
            )
        }, content = {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        })
    }) {
        Log.d("TAG", "HomeScreen: $it")
        when (state) {
            is HomeUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is HomeUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error fetching cases.")
                }
            }
            is HomeUiState.Success -> {
                val cases = (state as HomeUiState.Success).cases
                if (cases.isNotEmpty()) {
                    Log.d("Get Home", "onGetCases:  Got data ${cases.size}")
                    val scrollState = rememberSaveable { mutableStateOf(0) }
                    val columnState = rememberLazyListState(scrollState.value)
                    CasesList(
                        cases = cases,
                        navController = navController,
                        columnState = columnState
                    )
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No cases found.")
                    }
                }
            }
            else -> {

            }
        }
    }
}

fun onPermissionGranted() {

}

@Composable
fun CasesList(
    cases: List<Case>,
    modifier: Modifier = Modifier,
    columnState: LazyListState,
    navController: NavHostController
) {
    LazyColumn(
        state = columnState, modifier = modifier.fillMaxSize()
    ) {
        items(cases) { case ->
            // Display the details of each case in a Composable
            CaseCard(case = case, navController) {

            }
        }
    }
}


