package space.dlsunity.arctour.presenter.screens.main_container.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.jivesoftware.smackx.vcardtemp.packet.VCard
import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.domain.usecases.user.*
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.auxiliary.Event

class ProfileViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteAllUsersUseCase: DeleteAllUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel() {

    var vCard: VCard? = null

    private val _needSaveUser: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val needSaveUser: LiveData<Event<Boolean>>
        get() = _needSaveUser

    private val _userWasSaved: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val userWasSaved: LiveData<Event<Boolean>>
        get() = _userWasSaved

    private val _userWasLoaded: MutableLiveData<Event<User>> = MutableLiveData<Event<User>>()
    val userWasLoaded: LiveData<Event<User>>
        get() = _userWasLoaded

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    var mode = Mode.Read

    fun saveUser(user: User){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                saveUserUseCase.invoke(user).let {
                    _userWasSaved.postValue(Event(true))
                }
            }
        }
    }

    fun deleteUser(user: User){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                deleteUserUseCase.invoke(user)
            }
        }
    }

    fun deleteAllUsers(){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                deleteAllUsersUseCase.invoke()
            }
        }
    }

    fun getUserById(id: String){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                getUserByIdUseCase.invoke(id).let {
                    _userWasLoaded.postValue(Event(it))
                }
            }
        }
    }

    fun getAllUsers(){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
               getAllUsersUseCase.invoke()
            }
        }
    }

    fun needSaveUser(){

    }
}

enum class Mode{ Read, Edit }