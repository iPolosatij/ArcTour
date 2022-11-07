package space.dlsunity.arctour.presenter.screens.main_container.screens.notes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.destinations.MainDestination
import space.dlsunity.arctour.utils.auxiliary.Event

class NoteListViewModel(
    private val localContext: Context,
): BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<MainDestination>()
    val navigateCommander: SharedFlow<MainDestination> = _navigateCommander.asSharedFlow()

    private val _workoutListDownloaded: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val workoutListDownloaded: LiveData<Event<Boolean>>
        get() = _workoutListDownloaded

    private val _showAlert: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val showAlert: LiveData<Event<String>>
        get() = _showAlert

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    fun downloadAllWorkout(){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {

            }
        }
    }
}
