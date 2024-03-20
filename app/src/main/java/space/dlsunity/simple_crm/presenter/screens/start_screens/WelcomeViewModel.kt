package space.dlsunity.simple_crm.presenter.screens.start_screens

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.simple_crm.back4app.data.User
import space.dlsunity.simple_crm.back4app.manager.Back4AppAuthorisationManager
import space.dlsunity.simple_crm.back4app.state.AuthorisationState
import space.dlsunity.simple_crm.domain.usecases.users.GetAllUserUseCase
import space.dlsunity.simple_crm.domain.usecases.users.SaveUserUseCase
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseViewModel

class WelcomeViewModel (
    private val saveUserUseCase: SaveUserUseCase,
    private val getAllUserUseCase: GetAllUserUseCase,
): BaseViewModel() {

    var user: User? = null
    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _authorisationCallBack = MutableSharedFlow<AuthorisationState>()
    val authorisationCallBack: SharedFlow<AuthorisationState> =
        _authorisationCallBack.asSharedFlow()

    private val authorisationManager = Back4AppAuthorisationManager(_authorisationCallBack)

    fun signIn(login: String, password:String, needSetPin: Boolean) {
        authorisationManager.login(login, password, needSetPin)
    }
    fun signUp(login: String, password:String, email: String) {
        authorisationManager.signUp(login, password, email)
    }

    fun saveUser(user: User){
        CoroutineScope(Dispatchers.Default).launch { saveUserUseCase.invoke(user) }
    }

    fun setPin(pin: String, sessionToken: String?){
        user?.let {
            CoroutineScope(Dispatchers.Default).launch {
                saveUserUseCase.invoke(it.copy(pinCode = pin))
                _authorisationCallBack.emit(AuthorisationState.SuccessPinCode(sessionToken, false))
            }
        }
    }

    fun enterPin(pin: String, needSetPin: Boolean){
        user?.let {
            CoroutineScope(Dispatchers.Main).launch {
                if (pin == it.pinCode)
                    _authorisationCallBack.emit(AuthorisationState.SuccessPinCode(null, needSetPin ))
            }
        }
    }

    fun getUser(){
        CoroutineScope(Dispatchers.Default).launch {
            getAllUserUseCase.invoke().let {
                for(i in it){
                    user = i
                }
                user?.let {user->
                    if(user.pinCode.isNotEmpty()){
                        CoroutineScope(Dispatchers.Main).launch {
                            _authorisationCallBack.emit(AuthorisationState.ShowPinCode)
                        }
                    }
                }
            }
        }
    }

    fun toMain(sessionToken: String) {
        viewModelScope.launch {
            _navigateCommander.emit(AppDestination.ToMain)
        }
    }

}