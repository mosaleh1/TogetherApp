package tech.mosaleh.together.domain.model

enum class CaseType {
    Human, Animal,
}

data class Case(
    val id: String = "",
    val caseName: String = "",
    val imageUrl: String = "",
    val caseAddress: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val type: CaseType = CaseType.Human,
    val caseNeeds: String = ""
) {
}
