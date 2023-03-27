package tech.mosaleh.together.data.data_sources.remote.database

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.database.*
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.utils.RemoteDatabaseService

class RemoteDatabaseServiceImpl(
    private val database: FirebaseDatabase
) : RemoteDatabaseService {

    override fun getCases(): Flow<Resource<List<Case>>> = callbackFlow {
        val ref = database.getReference("cases")

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
}