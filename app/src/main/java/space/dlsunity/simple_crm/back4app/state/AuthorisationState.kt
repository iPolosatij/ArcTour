package space.dlsunity.simple_crm.back4app.state

import space.dlsunity.simple_crm.back4app.data.User

sealed class AuthorisationState{
    data object Processing: AuthorisationState()
    class Success(val sessionToken: String, val needSetPin: Boolean, val user: User?) : AuthorisationState()
    class AuthError(val message: String?): AuthorisationState()
    data object Logout: AuthorisationState()
    class SuccessPinCode(val sessionToken: String?, val needSetPin: Boolean): AuthorisationState()
    data object ShowPinCode: AuthorisationState()

    class SignUpSuccess(val login: String, val password: String, val user: User) : AuthorisationState()
}
