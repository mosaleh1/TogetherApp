package tech.mosaleh.together.presentation.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.presentation.screens.home.components.CaseCard
import tech.mosaleh.together.presentation.screens.utils.Screens

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val state = viewModel.state
    Log.d(TAG, "HomeScreen: 1")
    var isFirstLaunch by remember { mutableStateOf(true) }
    Log.d(TAG, "HomeScreen: 2 $isFirstLaunch")
    if (isFirstLaunch) {
        LaunchedEffect(key1 = context) {
            Log.d(TAG, "HomeScreen: 3 launched effect")
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                navController.navigate(
                    route = Screens.Login.route
                ) {
                    popUpTo(Screens.Home.route) {
                        inclusive = true
                    }
                }
                Log.d(TAG, "HomeScreen: 4 launched effect Done")
            }
            isFirstLaunch = false
            viewModel.onEvent(HomeEvents.GetCases)
        }
    }
    Log.d(TAG, "HomeScreen: 3 launched effect")

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Cases") }, actions = {
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.FilterList, contentDescription = "Filter")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { /* Filter by Case Type */ }) {
                    Text("Filter by Case Type")
                }
                DropdownMenuItem(onClick = { /* Filter by Case Needs */ }) {

                }
            }
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(
                    route = Screens.Home.route
                )
            }) {
                Icon(Icons.Filled.Logout, contentDescription = "Log Out")
            }
        })
    }, modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(
                route = Screens.AddCase.route
            )
        }, content = {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        })
    }) {
        Log.d(TAG, "HomeScreen: $it ")
        when (state) {

            is HomeUiState.Loading -> {
                Log.d(TAG, "HomeScreen: Loading ")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is HomeUiState.Error -> {
                Log.d(TAG, "HomeScreen: Failed ")
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error fetching cases.")
                }
            }
            is HomeUiState.Success -> {
                Log.d(TAG, "HomeScreen: Success ")
                val cases = (state).cases
                if (cases.isNotEmpty()) {
                    Log.d(TAG, "onGetCases:  Got data ${cases.size}")
                    val scrollState = rememberSaveable { mutableStateOf(0) }
                    val columnState = rememberLazyListState(scrollState.value)
                    CasesList(
                        cases = cases, navController = navController, columnState = columnState
                    )
                } else {
                    Log.d(TAG, "HomeScreen: ")
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

