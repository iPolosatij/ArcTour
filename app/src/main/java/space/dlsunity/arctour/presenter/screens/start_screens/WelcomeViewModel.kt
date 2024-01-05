package space.dlsunity.arctour.presenter.screens.start_screens

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.arctour.back4app.manager.Back4AppAuthorisationManager
import space.dlsunity.arctour.back4app.state.AuthorisationState
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel

class WelcomeViewModel: BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

private val _authorisationCallBack = MutableSharedFlow<AuthorisationState>()
    val authorisationCallBack: SharedFlow<AuthorisationState> = _authorisationCallBack.asSharedFlow()

    private val authorisationManager: Back4AppAuthorisationManager = Back4AppAuthorisationManager(_authorisationCallBack)

    fun signIn(login: String, password:String) {
        authorisationManager.login(login, password)
    }
    fun signUp(login: String, password:String, email: String) {
        authorisationManager.signUp(login, password, email)
    }

    fun pin(pin : String){

    }

    fun setPin(pin : String){

    }

    fun toMain(sessionToken: String) {
        viewModelScope.launch {
            _navigateCommander.emit(AppDestination.ToMain)
        }
    }

}