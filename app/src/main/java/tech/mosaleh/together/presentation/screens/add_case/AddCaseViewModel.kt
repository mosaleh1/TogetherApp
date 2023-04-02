package tech.mosaleh.together.presentation.screens.add_case

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.use_cases.InsertCaseUseCase
import tech.mosaleh.together.domain.use_cases.UploadCaseImageUseCase
import tech.mosaleh.together.presentation.screens.utils.ValidationEvents
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class AddCaseViewModel
@Inject constructor(
    private val insertCase: InsertCaseUseCase,
    private val uploadImage: UploadCaseImageUseCase,
    private val app: Application
) : AndroidViewModel(app) {
    private val contentResolver = app.contentResolver
    var state by mutableStateOf(AddCaseUiState())

    //channel for sending data to UI
    private val addCaseEventsChannel = Channel<ValidationEvents>()
    val validationEvent = addCaseEventsChannel.receiveAsFlow()

    fun onEvent(event: AddCaseEvents) {
        when (event) {
            is AddCaseEvents.CaseAddressChanged -> {
                state = state.copy(
                    caseAddress = event.newAddress
                )
            }
            is AddCaseEvents.CaseDescriptionChanged -> {
                state = state.copy(
                    caseDescription = event.newDescription
                )
            }
            is AddCaseEvents.CaseNameChanged -> {
                state = state.copy(
                    caseName = event.caseName
                )
            }
            is AddCaseEvents.CaseNeedsChanged -> {
                state = state.copy(
                    caseNeeds = event.caseNeeds
                )
            }
            is AddCaseEvents.CaseTypeChanged -> {
                state = state.copy(
                    caseType = event.caseType
                )
            }
            is AddCaseEvents.ImageUriChanged -> {
                viewModelScope.launch {
                    state = state.copy(
                        imageUrl = event.imageUri
                    )
                }
            }
            AddCaseEvents.Insert -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        //validate data
        val addressValidator = state.validateCaseAddress()
        val descriptionValidator = state.validateCaseDescription()
        val hasError = listOf(
            addressValidator, descriptionValidator
        ).any {
            !it.successful
        }
        //if valid
        if (hasError) {
            state = state.copy(
                caseDescriptionError = descriptionValidator.errorMessage ?: ""
            )
            state = state.copy(
                caseAddressError = addressValidator.errorMessage ?: ""
            )
            return
        }
        // submit to firebase
        state = state.copy(
            caseDescriptionError = null
        )
        state = state.copy(
            caseAddressError = null
        )

        viewModelScope.launch {
            val case = Case(
                generateCaseId(),
                caseName = state.caseName,
                caseNeeds = state.caseNeeds,
                caseAddress = state.caseAddress,
                imageUrl = state.imageUrl ?: "",
                type = state.caseType,
                status = state.caseStatus,
                caseDescription = state.caseDescription,
            )
            insertCase.invoke(case, getByteArray()).collectLatest {
                when (it) {

                    is Resource.Success -> {
                        addCaseEventsChannel.send(ValidationEvents.Success)
                    }
                    is Resource.Error -> {
                        addCaseEventsChannel.send(
                            ValidationEvents.Failure(
                                it.message ?: "Error while inserting the case "
                            )
                        )
                    }
                    is Resource.Loading -> {
                        addCaseEventsChannel.send(ValidationEvents.Loading)
                    }
                }
            }
        }
        //else
    }

    private suspend fun getByteArray(): ByteArray? {
        if (state.imageUrl.isNullOrBlank()) {
            return null
        }
        return viewModelScope.async {
            // Convert URI to Bitmap
            val uri = Uri.parse(state.imageUrl)
            contentResolver
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.toByteArray()
        }.await()
    }


    private fun generateCaseId(): String {
        return "Together" + FirebaseAuth.getInstance().currentUser!!.uid.subSequence(
            0,
            7
        ) + state.caseAddress.lat + state.caseAddress.lng
    }

}







