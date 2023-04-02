package tech.mosaleh.together.data.data_sources.remote.database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import tech.mosaleh.together.domain.model.User

class FireStoreClientImpl : FireStoreClient {

    override fun updateUser(user: User) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(user.id)
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "User added successfully") }
            .addOnFailureListener { Log.e(TAG, "Error adding user ") }

    }

    companion object {
        private const val TAG = "FireStoreClientImpl"
    }


}