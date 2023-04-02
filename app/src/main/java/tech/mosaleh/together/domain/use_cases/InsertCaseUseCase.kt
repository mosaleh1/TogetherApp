package tech.mosaleh.together.domain.use_cases

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.repository.DetailsRepository
import java.util.*
import kotlin.math.log

class InsertCaseUseCase(
    private val repository: DetailsRepository,
) {
    suspend operator fun invoke(case: Case, byteArray: ByteArray?): Flow<Resource<Unit>> =
        callbackFlow {
            trySend(Resource.Loading())
            var imageUrl = ""
            Log.d("InsertUse", "invoked inserted ")
            if (byteArray != null) {
                Log.d("InsertUse", "invoked: byte arr not null ")
                val res = repository.uploadImageToServer(byteArray)
                res.data?.let {
                    imageUrl = it
                    Log.d("InsertUse", "imageUrl is not null: $it ")
                }
            }
            Log.d("InsertUse", "inserting new case ${case.imageUrl}")
            val newCase = case.copy(imageUrl = imageUrl)
            Log.d("InsertUse", "Done: ")
            trySend(repository.insertCase(newCase))
            awaitClose()
        }
}