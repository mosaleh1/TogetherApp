package tech.mosaleh.together.domain.use_cases

import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.repository.DetailsRepository

class UploadCaseImageUseCase(
    private val repository: DetailsRepository,
) {

    suspend operator fun invoke(byteArray: ByteArray): Resource<String> {
        return repository.uploadImageToServer(byteArray)
    }

}