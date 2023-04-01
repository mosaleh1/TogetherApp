package tech.mosaleh.together.presentation.screens.home

import tech.mosaleh.together.domain.model.Case

sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()
    data class Success(val cases: List<Case> = emptyList()) : HomeUiState()
    data class Error(val message: String) : HomeUiState()

}