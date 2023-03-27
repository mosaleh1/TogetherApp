package tech.mosaleh.together.presentation.screens.home

import tech.mosaleh.together.domain.model.Case

data class HomeUiState(
    val isLoading: Boolean = false,
    val cases: List<Case> = emptyList(),
    val error: String? = null
)