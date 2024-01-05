package space.dlsunity.arctour.back4app.state

sealed class AuthorisationState{
    object Processing: AuthorisationState()
    class Success(val sessionToken: String) : AuthorisationState()
    class AuthError(val message: String?): AuthorisationState()
    object Logout: AuthorisationState()

    class SignUpSuccess(val login: String, val password: String) : AuthorisationState()
}
