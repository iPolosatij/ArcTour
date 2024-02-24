package space.dlsunity.arctour.back4app.state

import space.dlsunity.arctour.back4app.data.User

sealed class AuthorisationState{
    object Processing: AuthorisationState()
    class Success(val sessionToken: String) : AuthorisationState()
    class AuthError(val message: String?): AuthorisationState()
    object Logout: AuthorisationState()
    object SuccessPinCode: AuthorisationState()
    object ShowPinCode: AuthorisationState()

    class SignUpSuccess(val login: String, val password: String, val user: User) : AuthorisationState()
}
