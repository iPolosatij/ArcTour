package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.*
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.destinations.MainDestination
import space.dlsunity.arctour.utils.auxiliary.Event

class TournamentViewModel: BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<MainDestination>()
    val navigateCommander: SharedFlow<MainDestination> = _navigateCommander.asSharedFlow()


    private val _showAlert: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val showAlert: LiveData<Event<String>>
        get() = _showAlert

    var viewListTarget: ArrayList<Tournament>? = null
    var viewListLaps: ArrayList<Tournament>? = null

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

}