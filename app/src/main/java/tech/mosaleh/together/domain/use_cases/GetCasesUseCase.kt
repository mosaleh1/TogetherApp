package tech.mosaleh.together.domain.use_cases

import kotlinx.coroutines.flow.Flow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.repository.HomeRepository

class GetCasesUseCase(private val repository: HomeRepository) {

    suspend operator fun invoke(): Resource<List<Case>>
    {
        return repository.getCasesFromFirebase()
    }
}