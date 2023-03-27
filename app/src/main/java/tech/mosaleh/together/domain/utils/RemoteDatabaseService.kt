package tech.mosaleh.together.domain.utils

import kotlinx.coroutines.flow.Flow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case

interface RemoteDatabaseService {

    fun getCases(): Flow<Resource<List<Case>>>

    suspend fun insertCase(case: Case): Resource<Any>

}