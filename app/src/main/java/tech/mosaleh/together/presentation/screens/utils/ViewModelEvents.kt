package tech.mosaleh.together.presentation.screens.utils

sealed class ViewModelEvents {
        object Success : ViewModelEvents()
        object Loading : ViewModelEvents()
        class Failure(val message : String) : ViewModelEvents()
}