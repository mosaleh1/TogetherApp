package tech.mosaleh.together.data.repository

import tech.mosaleh.together.data.data_sources.remote.database.RemoteDatabaseServiceImpl
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.repository.DetailsRepository
import tech.mosaleh.together.domain.utils.RemoteDatabaseService
import java.io.ByteArrayOutputStream

class DetailsRepositoryImpl(
    private val databaseService: RemoteDatabaseService
) : DetailsRepository {

    override suspend fun insertCase(case: Case): Resource<Unit> {
        return databaseService.insertCase(case)
    }

    override suspend fun uploadImageToServer(
        byteArray: ByteArray
    ): Resource<String> {
        return databaseService.uploadImageToServer(byteArray)
    }

}