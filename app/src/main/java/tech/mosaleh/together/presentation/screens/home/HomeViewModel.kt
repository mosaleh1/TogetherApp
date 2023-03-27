package tech.mosaleh.together.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private var _state = mutableStateOf(HomeUiState())
    val state: State<HomeUiState> get() = _state


    fun onEvent(event: HomeEvents) {
        when (event) {
            is HomeEvents.GetCases -> {
                onGetCases()
            }
        }
    }

    private fun onGetCases() {
        viewModelScope.launch {
            getCases().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = HomeUiState(isLoading = true)
                    }
                    is Resource.Error -> {
                        _state.value = HomeUiState(
                            error = result.message ?: "Error while getting the data"
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { newCases ->
                            val oldList = _state.value.cases
                            val newCaseIds = newCases.map { it.id }
                            val oldCaseIds = oldList.map { it.id }

                            // Get the IDs of any new cases
                            val newIds = newCaseIds.subtract(oldCaseIds)

                            if (newIds.isNotEmpty()) {
                                // Filter the new cases by the new IDs and update the state
                                val newCasesToSubmit = newCases.filter { newIds.contains(it.id) }
                                val updatedCases = oldList + newCasesToSubmit
                                _state.value = HomeUiState(cases = updatedCases)
                            }
                        }
                    }
                }
            }
        }
    }


}