package tech.mosaleh.together.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class CaseType {
    Human, Animal,
}

enum class CaseStatus {
    Heading, Still, Served
}

enum class CaseNeeds {
    Adoption, Clothes, Food, HeathCare, Other
}


@Parcelize
data class Case(
    val id: String = "",
    val caseName: String = "",
    val imageUrl: String = "",
    val caseAddress: CaseAddress = CaseAddress(),
    val type: CaseType = CaseType.Human,
    val status: CaseStatus = CaseStatus.Still,
    val caseNeeds: CaseNeeds = CaseNeeds.Other,
    val caseDescription: String = ""
) : Parcelable
