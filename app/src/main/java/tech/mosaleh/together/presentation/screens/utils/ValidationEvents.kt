package tech.mosaleh.together.presentation.screens.utils

sealed class ValidationEvents {
        object Success : ValidationEvents()
        object Loading : ValidationEvents()
        class Failure(val message : String) : ValidationEvents()
}