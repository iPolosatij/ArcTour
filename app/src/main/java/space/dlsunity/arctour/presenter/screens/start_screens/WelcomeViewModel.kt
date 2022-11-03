package space.dlsunity.arctour.presenter.screens.start_screens

import android.content.Context
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.domain.usecases.tournaments.DeleteAllTournamentsUseCase
import space.dlsunity.arctour.domain.usecases.user.DeleteAllUsersUseCase
import space.dlsunity.arctour.domain.usecases.user.GetAllUsersUseCase
import space.dlsunity.arctour.domain.usecases.user.GetUserByIdUseCase
import space.dlsunity.arctour.domain.usecases.user.SaveUserUseCase
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.auxiliary.Event
import space.dlsunity.arctour.utils.extensions.toByteArray

class WelcomeViewModel(
    private val localContext: Context,
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteAllUsersUseCase: DeleteAllUsersUseCase,
    private val deleteAllTournamentsUseCase: DeleteAllTournamentsUseCase,
): BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    private val _message: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    private val _newUser: MutableLiveData<Event<User>> = MutableLiveData<Event<User>>()
    val newUser: LiveData<Event<User>>
        get() = _newUser


    val defaultPhotoProfile = localContext.getDrawable(R.drawable.ic_account_stock)?.toBitmap()?.toByteArray()
    var state : WelcomeState? = null

    init {
        loadData()
    }

    private fun loadData() {}

    fun toMain(){
        viewModelScope.launch {
            _navigateCommander.emit(AppDestination.ToMain)
        }
    }

    fun acceptUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            safeProgressHandler(error = _error) {
                if (user.name.isEmpty()){
                    getAllUsersUseCase.invoke().let{savedUser->
                        if (savedUser.size == 2){
                            if(savedUser[0].memberId == user.memberId && savedUser[0].password == user.password){
                                _message.postValue(Event(""))
                            }else{
                                _message.postValue(Event("Неверный пароль"))
                            }
                        }
                        else if(savedUser.size == 1){
                            if(savedUser[0].memberId == user.memberId && savedUser[0].password == user.password){
                                val userSave = savedUser[0].copy(memberId = "saved")
                                saveUserUseCase.invoke(userSave)
                                _message.postValue(Event(""))
                            }else{
                                _message.postValue(Event("Неверный пароль"))
                            }
                        }
                        else{
                            _message.postValue(Event("Пользователь не найден"))
                        }
                    }
                }else{
                    getAllUsersUseCase.invoke().let{savedUser->
                        if (savedUser.isEmpty()){
                            saveUserUseCase.invoke(user).let {
                                _message.postValue(Event(""))
                            }
                        }else {
                            if (savedUser[0].memberId == user.memberId) {
                                _message.postValue(Event(" Пользователь с такой почтой уже существует "))
                            } else {
                                _newUser.postValue(Event(user))
                            }
                        }
                    }
                }
            }
        }
    }

    fun loginNewUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            safeProgressHandler(error = _error) {
                delay(1000)
                saveUserUseCase.invoke(user).let {
                    delay(1000)
                    user.memberId = "saved"
                    saveUserUseCase.invoke(user).let {
                        delay(1000)
                        _message.postValue(Event(""))
                    }
                }
            }
        }
    }
}