package space.dlsunity.arctour.back4app.manager

import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.arctour.back4app.data.User
import space.dlsunity.arctour.back4app.state.AuthorisationState
import java.util.UUID

class Back4AppAuthorisationManager(
    private val callback: MutableSharedFlow<AuthorisationState>
)  {

    fun login(username: String, password: String, needSetPin: Boolean) {
        postAuthorisationState(AuthorisationState.Processing)
        ParseUser.logInInBackground(username,password) { parseUser: ParseUser?, e: ParseException? ->
            if (parseUser != null) {
                postAuthorisationState(AuthorisationState.Success(
                    parseUser.sessionToken,
                    needSetPin,
                    if (needSetPin) User(id = UUID.randomUUID().toString(), login = username, password = password) else null))
            } else {
                ParseUser.logOut()
                postAuthorisationState(AuthorisationState.AuthError(e?.message))
            }
        }
    }

    fun logout(){
        ParseUser.logOutInBackground { e: ParseException? ->
            if (e == null)
                postAuthorisationState(AuthorisationState.Logout)
            else
                postAuthorisationState(AuthorisationState.AuthError(e.message))
        }
    }

    fun signUp(username: String, password: String, email: String) {
        val user = ParseUser()
        user.username = username
        user.setPassword(password)
        user.email = email
        user.signUpInBackground {
            if (it == null) {
                ParseUser.logOut();
                val user = User(id = UUID.randomUUID().toString(), login = username, password = password)
                postAuthorisationState(AuthorisationState.SignUpSuccess(username, password, user))
            } else {
                ParseUser.logOut();
                postAuthorisationState(AuthorisationState.AuthError(it.message))
            }
        }
    }

    private fun postAuthorisationState(state: AuthorisationState){
        CoroutineScope(Dispatchers.Main).launch {
            callback.emit(state)
            this.cancel()
        }
    }
}