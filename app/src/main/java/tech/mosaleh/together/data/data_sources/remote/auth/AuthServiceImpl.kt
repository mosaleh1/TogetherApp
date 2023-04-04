package tech.mosaleh.together.data.data_sources.remote.auth

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.GlobalScope.coroutineContext
import tech.mosaleh.together.domain.utils.AuthService
import tech.mosaleh.together.domain.utils.AuthState
import kotlinx.coroutines.tasks.await
import tech.mosaleh.together.data.data_sources.remote.database.FireStoreClient
import tech.mosaleh.together.data.data_sources.remote.database.FireStoreClientImpl
import tech.mosaleh.together.domain.model.User
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthServiceImpl(
    private val authProvider:
    FirebaseAuth = FirebaseAuth.getInstance(),
    private val fireStoreClient: FireStoreClient = FireStoreClientImpl()
) :
    AuthService {

    override suspend fun signIn(email: String, password: String):
            AuthState = suspendCoroutine { continuation ->
        try {
            authProvider.signInWithEmailAndPassword(email, password).apply {
                addOnSuccessListener {
                    continuation.resume(AuthState.Success)
                }
                addOnFailureListener {
                    continuation.resume(AuthState.Failure(it.message ?: "Error while Login"))
                }
            }
        } catch (ex: FirebaseException) {
            continuation.resume(AuthState.Failure("X${ex.message ?: "Error while Login"}"))
        }
    }

    override suspend fun register(
        firstName: String, lastName: String, email: String, password: String
    ): AuthState = suspendCoroutine { continuation ->
        try {
            authProvider.createUserWithEmailAndPassword(email, password).apply {
                addOnSuccessListener { it ->
                    val request = UserProfileChangeRequest.Builder()
                    request.displayName = firstName + lastName

                    it.user?.let { usr ->
                        usr.updateProfile(
                            request.build()
                        )
                        val user = User(
                            id = usr.uid,
                            firstName = firstName,
                            lastName = lastName,
                            email = email,
                        )
                        fireStoreClient.uploadUserToFireStore(user)

                    }
                    continuation.resume(AuthState.Success)
                }
                addOnFailureListener {
                    continuation.resume(AuthState.Failure(it.message ?: "Error while Registration"))
                }
            }
        } catch (ex: FirebaseException) {
            continuation.resume(AuthState.Failure("X${ex.message ?: "Error while Registration"}"))
        }
    }
}