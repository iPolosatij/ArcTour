package space.dlsunity.simple_crm.presenter.screens.main_container.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import space.dlsunity.simple_crm.back4app.manager.Back4AppAuthorisationManager
import space.dlsunity.simple_crm.back4app.state.AuthorisationState
import space.dlsunity.simple_crm.data.room.data.UserEntity
import space.dlsunity.simple_crm.domain.usecases.users.DeleteAllUsersUseCase
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseViewModel
import space.dlsunity.simple_crm.presenter.screens.errors.ErrorModel
import space.dlsunity.simple_crm.utils.auxiliary.Event

class ProfileViewModel(
    private val deleteUsersUseCase: DeleteAllUsersUseCase
): BaseViewModel() {

    private val _needSaveUser: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val needSaveUser: LiveData<Event<Boolean>>
        get() = _needSaveUser

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    private val _authorisationCallBack = MutableSharedFlow<AuthorisationState>()
    val authorisationCallBack: SharedFlow<AuthorisationState> =
        _authorisationCallBack.asSharedFlow()

    private val authorisationManager = Back4AppAuthorisationManager(_authorisationCallBack)

    fun logOutProcedure(){
        authorisationManager.logout()
    }



    fun saveUser(userEntity: UserEntity){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {

            }
        }
    }

    fun logOut(){

    }

    fun needSaveUser(){

    }
}