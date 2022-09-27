package space.dlsunity.arctour.presenter.screens.main_screen.screens

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
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.auxiliary.Event

class ProfileViewModel(

): BaseViewModel() {

    var vCard: VCard? = null

    private val _needSaveUser: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val needSaveUser: LiveData<Event<Boolean>>
        get() = _needSaveUser

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    fun saveUser(user: User){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {

            }
        }
    }

    fun needSaveUser(){

    }
}