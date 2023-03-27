package tech.mosaleh.together.data.data_sources.remote.database

import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.database.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.utils.RemoteDatabaseService
import kotlinx.coroutines.tasks.await

class RemoteDatabaseServiceImpl(
    private val database: FirebaseDatabase
) : RemoteDatabaseService {
    private var ref: DatabaseReference = database.getReference("cases")

    override fun getCases(): Flow<Resource<List<Case>>> = callbackFlow {

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cases = mutableListOf<Case>()
                for (childSnapshot in snapshot.children) {
                    val case = childSnapshot.getValue(Case::class.java)
                    case?.let { cases.add(it) }
                }
                trySend(Resource.Success(cases))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        }
        ref.addListenerForSingleValueEvent(listener)

        awaitClose()
    }

    private lateinit var resource: Resource<Any>
    override suspend fun insertCase(case: Case): Resource<Any> {
        try {
            ref.push().setValue(case)
                .apply {
                    addOnSuccessListener {
                        resource = Resource.Success(Any())
                    }
                    addOnFailureListener {
                        resource = Resource.Error(it.message ?: "Can't insert Case")
                    }
                    await()
                }

        } catch (e: Exception) {
            resource = Resource.Error(e.message ?: "Can't insert case")
        }
        return resource
    }
}