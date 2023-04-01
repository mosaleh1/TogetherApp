package tech.mosaleh.together.data.data_sources.remote.database

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import tech.mosaleh.together.domain.core.Resource
import tech.mosaleh.together.domain.model.Case
import tech.mosaleh.together.domain.utils.RemoteDatabaseService
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class RemoteDatabaseServiceImpl(
    private val database: FirebaseDatabase,
) : RemoteDatabaseService {
    private var dbRef: DatabaseReference = database.getReference("cases")
    private val storageRef = FirebaseStorage.getInstance().getReference("/images")

    override suspend fun uploadImageToServer(byteArray: ByteArray): Resource<String> {
        val imageName = "image_" + SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(Date()) + ".jpg"

        return try {
            val imageRef = storageRef.child(imageName)
            val uploadTask = imageRef.putBytes(byteArray)
            val imageUrl = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                imageRef.downloadUrl
            }.await().toString()
            Resource.Success(imageUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = "Unable to upload the image")
        }
    }

    override suspend fun insertCase(case: Case): Resource<Unit> {
        var resource: Resource<Unit> = Resource.Error("")
        try {
            dbRef.push().setValue(case)
                .apply {
                    addOnSuccessListener {
                        resource = Resource.Success(Unit)
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


    override fun getCases(): Flow<Resource<List<Case>>> = callbackFlow {
        trySend(Resource.Loading())
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
        dbRef.addListenerForSingleValueEvent(listener)
        awaitClose()
    }

}