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
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                if (user.name.isEmpty()){
                    getAllUsersUseCase.invoke().let{savedUser->
                        if (savedUser.isNotEmpty()){
                            if(savedUser[0].memberId == user.memberId && savedUser[0].password == user.password){
                                _message.postValue(Event(""))
                            }else{
                                _message.postValue(Event("Неверный пароль"))
                            }
                        }else{
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
                                deleteAllUsersUseCase.invoke().let {
                                    deleteAllTournamentsUseCase.invoke()
                                }
                                delay(1000)
                                saveUserUseCase.invoke(user).let {
                                    _message.postValue(Event(""))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}