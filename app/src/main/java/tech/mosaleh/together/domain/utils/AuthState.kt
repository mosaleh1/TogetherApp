package tech.mosaleh.together.domain.utils

sealed class AuthState {
    object Success : AuthState()
    class Failure(val message:String) : AuthState()
}
