package tech.mosaleh.together.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
    }
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = {

        }, content = {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        })
    }) {
        val state = viewModel.state.value
        val scrollState = rememberSaveable { mutableStateOf(0) }
        val columnState = rememberLazyListState(scrollState.value)
        viewModel.onEvent(HomeEvents.GetCases)
        if (state.cases.isNotEmpty()) {
            CasesList(cases = state.cases, columnState = columnState)
        }
    }
}

@Composable
fun CasesList(
    cases: List<Case>, modifier: Modifier = Modifier, columnState: LazyListState
) {
    LazyColumn(
        state = columnState, modifier = modifier.fillMaxSize()
    ) {
        items(cases) { case ->
            // Display the details of each case in a Composable
            CaseCard(case = case) {

            }
        }
    }
}

