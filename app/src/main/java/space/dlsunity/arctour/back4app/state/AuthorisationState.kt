package space.dlsunity.arctour.back4app.state

sealed class AuthorisationState{
    object Processing: AuthorisationState()
    class Success(sessionToken: String) : AuthorisationState()
    class Error(message: String?): AuthorisationState()
    object Logout: AuthorisationState()

    object SignUpSuccess : AuthorisationState()
}
