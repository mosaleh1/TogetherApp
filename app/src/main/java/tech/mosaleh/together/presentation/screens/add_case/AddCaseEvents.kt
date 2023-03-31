package tech.mosaleh.together.presentation.screens.add_case

import tech.mosaleh.together.domain.model.CaseAddress
import tech.mosaleh.together.domain.model.CaseNeeds
import tech.mosaleh.together.domain.model.CaseType

sealed class AddCaseEvents {
    data class CaseNameChanged(val caseName: String) : AddCaseEvents()
    data class CaseAddressChanged(val newAddress: CaseAddress) : AddCaseEvents()
    data class CaseDescriptionChanged(val newDescription: String) : AddCaseEvents()
    data class CaseTypeChanged(val caseType: CaseType) : AddCaseEvents()
    data class CaseNeedsChanged(val caseNeeds: CaseNeeds) : AddCaseEvents()
    data class ImageUriChanged(val imageUri: String) : AddCaseEvents()
    object Insert : AddCaseEvents()
}
