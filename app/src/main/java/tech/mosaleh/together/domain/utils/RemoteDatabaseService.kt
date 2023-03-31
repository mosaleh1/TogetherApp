package tech.mosaleh.together.domain.utils

import kotlinx.coroutines.flow.Flow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import java.io.ByteArrayOutputStream

interface RemoteDatabaseService {

    fun getCases(): Flow<Resource<List<Case>>>

    suspend fun insertCase(case: Case): Resource<Unit>
    suspend fun uploadImageToServer(byteArray: ByteArray): Resource<String>

}