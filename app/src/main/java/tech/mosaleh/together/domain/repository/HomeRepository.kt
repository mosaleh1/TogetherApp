package tech.mosaleh.together.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case

interface HomeRepository {

    fun getCasesFromFirebase(): Flow<Resource<List<Case>>>

}