package tech.mosaleh.together.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.use_cases.GetCasesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCases: GetCasesUseCase
) : ViewModel() {

    var state by mutableStateOf<HomeUiState>(HomeUiState.Idle)

    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.GetCases -> {
                onGetCases()
            }
        }
    }

    private fun onGetCases() {
        viewModelScope.launch {
            Log.d("Get Home", "onGetCases:  Called")
            getCases().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.d("Get Home", "onGetCases:  Loading")
                        state = HomeUiState.Loading
                    }
                    is Resource.Error -> {

                        Log.d("Get Home", "onGetCases:  Error")
                        state = HomeUiState.Error(message = result.message ?: "Error")
                    }
                    is Resource.Success -> {
                        Log.d("Get Home", "onGetCases:  Success")
                        result.data!!.let { newCases ->
                            state = HomeUiState.Success(
                                cases = newCases
                            )
                        }
                    }
                }
            }
        }
    }


}