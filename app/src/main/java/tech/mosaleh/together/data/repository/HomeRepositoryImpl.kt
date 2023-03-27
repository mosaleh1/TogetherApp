package tech.mosaleh.together.data.repository

import kotlinx.coroutines.flow.Flow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.repository.HomeRepository
import tech.mosaleh.together.domain.utils.RemoteDatabaseService

class HomeRepositoryImpl(
    private val databaseService: RemoteDatabaseService
) : HomeRepository {

    override fun getCasesFromFirebase(): Flow<Resource<List<Case>>> {
        return databaseService.getCases()
    }

}