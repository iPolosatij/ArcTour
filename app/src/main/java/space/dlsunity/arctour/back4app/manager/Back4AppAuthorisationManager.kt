package space.dlsunity.arctour.back4app.manager

import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.arctour.back4app.state.AuthorisationState

class Back4AppAuthorisationManager(private val callback: MutableSharedFlow<AuthorisationState>)  {

    private fun login(username: String, password: String) {
        postAuthorisationState(AuthorisationState.Processing)
        ParseUser.logInInBackground(username,password) { parseUser: ParseUser?, e: ParseException? ->
            if (parseUser != null) {
                postAuthorisationState(AuthorisationState.Success(parseUser.sessionToken))
            } else {
                ParseUser.logOut()
                postAuthorisationState(AuthorisationState.Error(e?.message))
            }
        }
    }

    private fun logout(){
        ParseUser.logOutInBackground { e: ParseException? ->
            if (e == null)
                postAuthorisationState(AuthorisationState.Logout)
            else
                postAuthorisationState(AuthorisationState.Error(e?.message))
        }
    }

    private fun signUp(username: String, password: String, email: String) {
        val user = ParseUser()
        user.username = username
        user.setPassword(password)
        user.email = email
        user.signUpInBackground {
            if (it == null) {
                ParseUser.logOut();
              postAuthorisationState(AuthorisationState.SignUpSuccess)
            } else {
                ParseUser.logOut();
                postAuthorisationState(AuthorisationState.Error(it.message))
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