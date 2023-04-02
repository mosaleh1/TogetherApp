package tech.mosaleh.together.presentation.screens.add_case

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.mosaleh.together.domain.model.CaseAddress
import tech.mosaleh.together.domain.model.CaseNeeds
import tech.mosaleh.together.domain.model.CaseType
import tech.mosaleh.together.domain.use_cases.GetCompleteAddressUseCase
import tech.mosaleh.together.domain.utils.ADDRESS_EXTRA
import tech.mosaleh.together.presentation.components.*
import tech.mosaleh.together.presentation.screens.utils.Screens
import tech.mosaleh.together.presentation.screens.utils.ValidationEvents

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddCaseScreen(
    navController: NavController, viewModel: AddCaseViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val selectedImageBitMap = remember { mutableStateOf<ImageBitmap?>(null) }
    val state = viewModel.state

    val secondScreenResult =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<LatLng>(
            ADDRESS_EXTRA
        )?.observeAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf("")
    }
    var isDoneSuccessfully by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collectLatest {
            when (it) {
                is ValidationEvents.Loading -> {
                    isLoading = true
                }
                is ValidationEvents.Failure -> {
                    Log.d("AddCaseScreen", "AddCaseScreen: Failure ")
                    isLoading = false
                    isError = it.message
                }
                is ValidationEvents.Success -> {
                    isLoading = false
                    Log.d("AddCaseScreen", "AddCaseScreen: Success ")
                    isDoneSuccessfully = true
                }
            }
        }
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Add Case") }) },
    ) {
        val scrollState = rememberScrollState()
        Box {
            if (isLoading)
                ConditionalProgressBar {
                    isLoading = false
                }
            if (isError.isNotBlank()) {
                ErrorDialog(errorMessage = isError) {
                    isLoading = false
                }
            }
            if (isDoneSuccessfully) {
                ThankYouDialog(message = "thank you for adding a new Case") {
                    navController.navigate(
                        route = Screens.Home.route
                    )
                    isDoneSuccessfully = false
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //image
                ImagePicker(selectedImageBitMap.value, onImageSelected = { uri ->
                    uri?.let {
                        selectedImageBitMap.value =
                            getBitmapFromUri(context = context, uri = it)?.asImageBitmap()
                        viewModel.onEvent(AddCaseEvents.ImageUriChanged(uri.toString()))
                    }
                })
                CommonTextField(
                    label = "Case Name",
                    value = state.caseName,
                    isError = false,
                    error = "",
                    onChanged = {
                        viewModel.onEvent(AddCaseEvents.CaseNameChanged(it))
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
                CommonTextField(
                    label = "Case Details",
                    value = state.caseDescription,
                    isError = state.caseDescriptionError != null,
                    error = "",
                    onChanged = {
                        viewModel.onEvent(AddCaseEvents.CaseDescriptionChanged(it))
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                )
                OutlinedTextField(
                    isError = state.caseAddressError != null,
                    value = state.caseAddress.addressStr,
                    readOnly = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Place,
                            contentDescription = "Maps",
                            Modifier.clickable {
                                navController.navigate(
                                    route = Screens.LocationPicker.route
                                )
                            })
                    },
                    label = {
                        Text("Case Location")
                    },
                    onValueChange = {

                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                )
                val caseTypes = CaseType.values().map { it.name }
                val caseNeeds = CaseNeeds.values().map { it.name }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 10.dp
                        )
                ) {
                    EnumDropdownRow(
                        Modifier.weight(1F),
                        label = "Case Type",
                        enumValues = caseTypes,
                        selectedValue = state.caseType.toString(),
                        onValueChange = {
                            viewModel.onEvent(
                                AddCaseEvents.CaseTypeChanged(enumValueOf(it))
                            )
                        },
                    )
                    EnumDropdownRow(
                        Modifier.weight(1f),
                        label = "Case Needs",
                        enumValues = caseNeeds,
                        selectedValue = state.caseNeeds.toString(),
                        onValueChange = {
                            viewModel.onEvent(AddCaseEvents.CaseNeedsChanged(enumValueOf(it)))
                        },
                    )
                }
                Button(modifier = Modifier.padding(top = 10.dp),
                    onClick = { viewModel.onEvent(AddCaseEvents.Insert) }) {
                    Text(text = "Submit")
                }
            }

        }
    }

    viewModel.validationEvent

    val scope = rememberCoroutineScope()
    secondScreenResult?.value?.let { latLng ->
        // Read the result
        scope.launch {
            val getAddress =
                GetCompleteAddressUseCase(context).invoke(latLng.latitude, latLng.longitude)
            val caseAddress = CaseAddress(
                latLng.latitude, latLng.longitude, addressStr = getAddress
            )
            viewModel.onEvent(AddCaseEvents.CaseAddressChanged(caseAddress))
        }

    }


}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    val inputStream = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}










