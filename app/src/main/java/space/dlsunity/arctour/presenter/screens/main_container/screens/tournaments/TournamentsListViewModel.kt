package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.domain.usecases.*
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.destinations.MainDestination
import space.dlsunity.arctour.utils.auxiliary.Event

class TournamentsListViewModel(
    private val localContext: Context,
    private val getAllTournamentsUseCase: GetAllTournamentsUseCase,
    private val saveTournamentUseCase: SaveTournamentUseCase,
    private val deleteTournamentUseCase: DeleteTournamentUseCase,
    private val getTournamentByIdUseCase: GetTournamentByIdUseCase,
    private val deleteAllTournamentsUseCase: DeleteAllTournamentsUseCase
): BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<MainDestination>()
    val navigateCommander: SharedFlow<MainDestination> = _navigateCommander.asSharedFlow()


    private val _showAlert: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val showAlert: LiveData<Event<String>>
        get() = _showAlert

    private val _finishLoadTournaments: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val finishLoadTournaments: LiveData<Event<Boolean>>
        get() = _finishLoadTournaments

    var viewList: ArrayList<Tournament> = arrayListOf()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    fun loadTournamentFromDB(){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error){
                getAllTournamentsUseCase.invoke().let {
                    viewList.clear()
                    for (tournament in it){
                        viewList.add(tournament)
                    }
                    _finishLoadTournaments.postValue(Event(true))
                }
            }
        }
    }

}
