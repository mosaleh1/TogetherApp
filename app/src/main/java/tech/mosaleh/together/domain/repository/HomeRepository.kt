package tech.mosaleh.together.domain.repository

import kotlinx.coroutines.flow.Flow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case

interface HomeRepository {

    suspend fun getCasesFromFirebase(): Resource<List<Case>>

}