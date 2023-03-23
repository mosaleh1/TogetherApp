package tech.mosaleh.together.domain.utils

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)