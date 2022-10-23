package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import space.dlsunity.arctour.data.room.data.Lap
import space.dlsunity.arctour.data.room.data.Participant
import space.dlsunity.arctour.data.room.data.Target
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.domain.usecases.tournaments.SaveTournamentUseCase
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.auxiliary.Event

class CreateTournamentViewModel(
    private val localContext: Context,
    private val saveTournamentUseCase: SaveTournamentUseCase,
    ) : BaseViewModel() {

    private val _showAlert: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val showAlert: LiveData<Event<String>>
        get() = _showAlert

private val _tournamentWasSaved: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val tournamentWasSaved: LiveData<Event<Boolean>>
        get() = _tournamentWasSaved



    var viewListTarget: ArrayList<Target> = arrayListOf()
    var viewListLaps: ArrayList<Lap> = arrayListOf()
    var viewListParticipant: ArrayList<Participant> = arrayListOf()
    var photo: String = ""

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    fun saveTournament(tournament: Tournament){
        CoroutineScope(Dispatchers.Default).launch {
            safeProgressHandler(error = _error) {
                saveTournamentUseCase.invoke(tournament).let {
                    _tournamentWasSaved.postValue(Event(true))
                }
            }
        }
    }

}