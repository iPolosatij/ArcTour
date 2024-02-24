package space.dlsunity.arctour.presenter.screens.main_container

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.MenuItem
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import space.dlsunity.arctour.MainActivity
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.UserEntity
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.destinations.MainDestination
import space.dlsunity.arctour.utils.auxiliary.Event
import space.dlsunity.arctour.utils.tools.ImageManager
import space.dlsunity.arctour.utils.tools.ImagePiker

class MainContainerViewModel(
    private val localContext: Context
) : BaseViewModel() {

    //region EVENT

    private val _navigateCommander = MutableSharedFlow<MainDestination>()
    val navigateCommander: SharedFlow<MainDestination> = _navigateCommander.asSharedFlow()

    private val _activeScreen: MutableLiveData<Event<Int>> = MutableLiveData<Event<Int>>()
    val activeScreen: LiveData<Event<Int>>
        get() = _activeScreen

    private val _needShowBottomMenu: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()
    val needShowBottomMenu: LiveData<Event<Boolean>>
        get() = _needShowBottomMenu

    private val _logOut: MutableLiveData<Event<Boolean>> =
        MutableLiveData<Event<Boolean>>()
    val logOut: LiveData<Event<Boolean>>
        get() = _logOut

    private val _needAddBtn: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val needAddBtn: LiveData<Event<Boolean>>
        get() = _needAddBtn

    private val _needFindBtn: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val needFindBtn: LiveData<Event<Boolean>>
        get() = _needFindBtn

    private val _needLoader: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val needLoader: LiveData<Event<Boolean>>
        get() = _needLoader

    private val _userEntityWasSaved: MutableLiveData<Event<UserEntity>> = MutableLiveData<Event<UserEntity>>()
    val userEntityWasSaved: LiveData<Event<UserEntity>>
        get() = _userEntityWasSaved

    private val _title: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val title: LiveData<Event<String>>
        get() = _title

    private val _showAlert: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val showAlert: LiveData<Event<String>>
        get() = _showAlert

    private val _userEntityDownloaded: MutableLiveData<Event<UserEntity>> = MutableLiveData<Event<UserEntity>>()
    val userEntityDownloaded: LiveData<Event<UserEntity>>
        get() = _userEntityDownloaded

    private val _needUpdateProfilePhoto: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val needUpdateProfilePhoto: LiveData<Event<Boolean>>
        get() = _needUpdateProfilePhoto

    private val _icProfilePhoto: MutableLiveData<Event<Bitmap>> = MutableLiveData<Event<Bitmap>>()
    val icProfilePhoto: LiveData<Event<Bitmap>>
        get() = _icProfilePhoto

    private val _profilePhoto: MutableLiveData<Event<Bitmap>> = MutableLiveData<Event<Bitmap>>()
    val profilePhoto: LiveData<Event<Bitmap>>
        get() = _profilePhoto

    //endregion

    //region VARIABLES
    var changedAvatar = false

    var userEntity: UserEntity? = null

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    var loaderMessage = ""

    var menuItemActual: MenuItem? = null

    var webViewIsOpen = false

    var mode: Mode = Mode.Read

    val defaultPhotoProfile = localContext.getDrawable(R.drawable.ic_profile_image)?.toBitmap()

    var photoMain: Bitmap? = defaultPhotoProfile

    var counter = -1

    var new = ""

    //endregion

    //region FUNCTIONS
    fun setScreen(id: Int) {
        _activeScreen.postValue(Event(id))
    }

    private fun updateProfilePhoto(){
        _needUpdateProfilePhoto.postValue(Event(true))
    }

    fun setScreenTitle(screen: String) {
        _title.postValue(Event(screen))
    }

    fun needShowBottomMenu(needShow: Boolean) {
        _needShowBottomMenu.postValue(Event(needShow))
    }

    fun mainLoaderShow(show: Boolean, message: String) {
        loaderMessage = message
        _needLoader.postValue(Event(show))
    }

    fun showAddBtn(needShow: Boolean, what: String) {
        new = what
        _needAddBtn.postValue(Event(needShow))
    }

    fun showFindBtn(needShow: Boolean) {
        _needFindBtn.postValue(Event(needShow))
    }

    fun saveUser() {
        CoroutineScope(Dispatchers.IO).launch {
            safeProgressHandler(error = _error) {
                userEntity?.let {
                    _userEntityWasSaved.postValue(Event(it))
                }
            }
        }
    }

    private fun startTimer(count: Int) {
        counter = count
        CoroutineScope(Dispatchers.IO).launch {
            while (counter > 0) {
                delay(1000)
                counter--
            }
            if (counter == 0) {
                logOut()
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            CoroutineScope(Dispatchers.Default).launch {
                safeProgressHandler(error = _error) {
                    _logOut.postValue(Event(true))
                }
            }
        }
    }

    fun setBottomMenuIc(bitmap: Bitmap) {
        _icProfilePhoto.postValue(Event(bitmap))
    }

    fun changeProfilePhoto(fm: FragmentManager, holderId: Int, act: MainActivity) {
        CoroutineScope(Dispatchers.Main).launch {
            ImagePiker(act).getSinglePhotos(fm, holderId, _profilePhoto)
        }
    }

    fun setProfilePhoto(byteArray: ByteArray) {
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                val bitMap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                val radius = if (bitMap.width < bitMap.height) bitMap.width / 2 else bitMap.height / 2
                ImageManager.getCroppedBitmap(
                    bitMap,
                    bitMap.width / 2,
                    bitMap.height / 2,
                    radius
                )?.let { photo ->
                    photoMain = photo
                    updateProfilePhoto()
                    setBottomMenuIc(photo)
                    Log.d("MainViewModel --------->", "SetProfilePhoto")
                }

            }
        }
    }

    fun toWelcome(){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                _navigateCommander.emit(MainDestination.ToWelcome(true))
            }
        }
    }

    fun toCreate(){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                _navigateCommander.emit(MainDestination.ToCreateWorkCard)
            }
        }
    }

    private fun showLoader(need: Boolean) {
        _needLoader.postValue(Event(need))
    }

    fun stopTimer() {
        counter = -1
    }

    //endregion

    companion object {}
}

enum class Mode{ Read, Edit }


