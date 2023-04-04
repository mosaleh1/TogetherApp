package tech.mosaleh.together.data.data_sources.remote.database

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import tech.mosaleh.together.domain.model.User

class FireStoreClientImpl(
    private val db: FirebaseFirestore = Firebase.firestore
) : FireStoreClient {


    override fun updateLastKnownLocation(userId: String, location: LatLng) {
        val userRef = db.collection("users").document(userId)
        userRef.update("lastKnownLocation", GeoPoint(location.latitude, location.longitude))
            .addOnSuccessListener {
                Log.d(TAG, "Location updated for user $userId")
            }.addOnFailureListener {
                Log.e(TAG, "Failed to update location for user $userId: ${it.message}")
            }
    }


    override fun uploadUserToFireStore(user: User) {
        // Create a new document with a generated ID
        val user = db.collection("users").add(user).addOnSuccessListener {
            uploadFCMToken(user.id)
        }
    }

    override fun uploadFCMToken(uid: String) {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            val tokenData = hashMapOf(
                uid to it
            )
            db.collection("tokens").add(tokenData)
        }
    }

    companion object {
        private const val TAG = "FireStoreClientImpl"
    }
}