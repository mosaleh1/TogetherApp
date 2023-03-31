package tech.mosaleh.together.presentation.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM message
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(MyFirebaseMessagingService::class.simpleName, "onNewToken: $token")
    }
}