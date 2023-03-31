package tech.mosaleh.together.presentation.screens.add_case

import tech.mosaleh.together.domain.model.CaseAddress
import tech.mosaleh.together.domain.model.CaseNeeds
import tech.mosaleh.together.domain.model.CaseStatus
import tech.mosaleh.together.domain.model.CaseType
import tech.mosaleh.together.domain.utils.ValidationResult

data class AddCaseUiState(
    val caseName: String = "",
    val caseAddress: CaseAddress = CaseAddress(),
    val caseAddressError: String? = null,
    val caseType: CaseType = CaseType.Human,
    val caseTypeError: String? = null,
    val caseNeeds: CaseNeeds = CaseNeeds.Food,
    val imageUrl: String? = null,
    val caseDescription: String = "",
    val caseDescriptionError: String? = null,
    val caseStatus: CaseStatus = CaseStatus.Still
) {

    fun validateCaseAddress(): ValidationResult {
        return if (caseAddress.isValid()) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "case address can't be empty please select the location")
        }
    }

    fun validateCaseDescription(): ValidationResult {
        return if (caseDescription.isNotBlank()) {
            ValidationResult(true)
        } else {
            ValidationResult(false, "case description can't be empty")
        }
    }
}
