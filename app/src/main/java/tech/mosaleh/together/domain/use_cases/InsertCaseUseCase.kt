package tech.mosaleh.together.domain.use_cases

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.repository.DetailsRepository
import java.util.*

class InsertCaseUseCase(
    private val repository: DetailsRepository,
) {
    suspend operator fun invoke(case: Case): Resource<Unit> {
        return repository.insertCase(case)
    }
}