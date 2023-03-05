package space.dlsunity.arctour.data.network.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import space.dlsunity.arctour.R.string

class AccountHelper(private val auth: FirebaseAuth, val context: Context) {

    private lateinit var signInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.currentUser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                task.result.user?.let { user ->
                                    signUpWithEmailIsSuccessful(user)
                                }
                            } else {
                                task.exception?.let { exception ->
                                    checkSignUpWithEmailException(exception, email, password)
                                }
                            }
                        }
                }
            }
        }
    }

    private fun checkSignUpWithEmailException(exception: Exception, email: String, password: String){
        if (exception is FirebaseAuthWeakPasswordException) {
            if (exception.errorCode == FirebaseAuthConstants.ERROR_WEAK_PASSWORD) {
                Toast.makeText(context, FirebaseAuthConstants.ERROR_WEAK_PASSWORD, Toast.LENGTH_LONG).show()
            }
        }
        if (exception is FirebaseAuthUserCollisionException) {
            if (exception.errorCode == FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE) {
                //Toast.makeText(act, FirebaseAuthConstants.ERROR_EMAIL_ALREADY_IN_USE, Toast.LENGTH_LONG).show()
                linkEmailToG(email, password)
            }
        }

        if (exception is FirebaseAuthInvalidCredentialsException) {
            if (exception.errorCode == FirebaseAuthConstants.ERROR_INVALID_EMAIL) {
                Toast.makeText(context, FirebaseAuthConstants.ERROR_INVALID_EMAIL, Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun signUpWithEmailIsSuccessful(user: FirebaseUser){
        sendEmailVerification(user)
    }

    private fun linkEmailToG(email: String, password: String){
        val credential = EmailAuthProvider.getCredential(email, password)
        if (auth.currentUser != null) {
            auth.currentUser?.linkWithCredential(credential)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, context.resources.getString(string.link_done), Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(context, context.resources.getString(string.link_exception), Toast.LENGTH_LONG).show()
        }
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
        requestIdToken("context.getString(string.default_web_client_id)").requestEmail().build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun signInWithGoogle(){
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent
        //act.googleSignInLauncher.launch(intent)
    }

    fun signOutGoogle(){
        getSignInClient().signOut()
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.currentUser?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Sign in DONE", Toast.LENGTH_LONG).show()
                        //act.uiUpdate(task.result?.user)
                    } else {
                        Log.d("MyLog", "Google sign in Exception - ${task.exception}")
                    }
                }
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(context, context.resources.getString(string.sent_verification_done), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context, context.resources.getString(string.sent_verification_email_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signInWithEmail(email:String, password:String){
        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.currentUser?.delete()?.addOnCompleteListener {
                if (it.isSuccessful){
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            //act.uiUpdate(task.result?.user)
                        }else{
                            task.exception?.let {exception ->
                                checkSignInException(exception)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkSignInException(exception: Exception){
        //Log.d("MyLog", "Exception - ${task.exception}")
        Toast.makeText(context, context.resources.getString(string.sign_in_error), Toast.LENGTH_LONG).show()

        if (exception is FirebaseAuthInvalidCredentialsException) {
            if (exception.errorCode == FirebaseAuthConstants.ERROR_WRONG_PASSWORD) {
                Toast.makeText(context, FirebaseAuthConstants.ERROR_WRONG_PASSWORD, Toast.LENGTH_LONG).show()
            }
        }else if(exception is FirebaseAuthInvalidUserException) {
            if (exception.errorCode == FirebaseAuthConstants.ERROR_USER_NOT_FOUND) {
                Toast.makeText(context, FirebaseAuthConstants.ERROR_USER_NOT_FOUND, Toast.LENGTH_LONG).show()
            }
            if (exception.errorCode == FirebaseAuthConstants.ERROR_USER_DISABLED) {
                Toast.makeText(context, FirebaseAuthConstants.ERROR_USER_DISABLED, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signInAnonymously(listener: Listener){
        auth.signInAnonymously().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(context, "guest entry", Toast.LENGTH_LONG).show()
                listener.onComplete()
            }else{
                Toast.makeText(context, "guest entry failed", Toast.LENGTH_LONG).show()
            }
        }
    }

    interface Listener{
        fun onComplete()
    }
}

