package tech.mosaleh.together.domain.repository

import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import java.io.ByteArrayOutputStream

interface DetailsRepository {

    suspend fun insertCase(case: Case): Resource<Unit>


    suspend fun uploadImageToServer(byteArray: ByteArray): Resource<String>
}