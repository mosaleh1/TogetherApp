package tech.mosaleh.together.data.data_sources.remote.auth

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import tech.mosaleh.together.domain.utils.AuthService
import tech.mosaleh.together.domain.utils.AuthState
import kotlinx.coroutines.tasks.await

class AuthServiceImpl(private val authProvider: FirebaseAuth) : AuthService {
    private lateinit var state: AuthState

    override suspend fun signIn(email: String, password: String): AuthState {
        try {
            authProvider.signInWithEmailAndPassword(email, password).apply {
                addOnSuccessListener {
                    state = AuthState.Success
                }
                addOnFailureListener {
                    state = AuthState.Failure(it.message ?: "Error while Login")
                }
                await()
            }
        } catch (ex: FirebaseException) {
            state = AuthState.Failure("X${ex.message ?: "Error while Login"}")
        }
        return state
    }

    override suspend fun registration(email: String, password: String): AuthState {
        try {
            authProvider.createUserWithEmailAndPassword(email, password).apply {
                addOnSuccessListener {
                    state = AuthState.Success
                }
                addOnFailureListener {
                    state = AuthState.Failure(it.message ?: "Error while Registration")
                }
                await()
            }
        } catch (ex: FirebaseException) {
            state = AuthState.Failure("X${ex.message ?: "Error while Registration"}")
        }
        return state
    }
}