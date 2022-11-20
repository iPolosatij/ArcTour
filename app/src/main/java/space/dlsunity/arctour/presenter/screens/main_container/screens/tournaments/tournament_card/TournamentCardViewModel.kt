package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import space.dlsunity.arctour.data.room.data.*
import space.dlsunity.arctour.databinding.ItemPartListBinding
import space.dlsunity.arctour.domain.usecases.tournaments.GetAllTournamentsUseCase
import space.dlsunity.arctour.domain.usecases.tournaments.GetTournamentByIdUseCase
import space.dlsunity.arctour.domain.usecases.tournaments.SaveTournamentUseCase
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.auxiliary.Event
import java.util.*

class TournamentCardViewModel (
    private val localContext: Context,
    private val getAllTournamentsUseCase: GetAllTournamentsUseCase,
    private val getTournamentByIdUseCase: GetTournamentByIdUseCase,
    private val saveTournamentUseCase: SaveTournamentUseCase,
) : BaseViewModel() {

    private val _showAlert: MutableLiveData<Event<String>> = MutableLiveData<Event<String>>()
    val showAlert: LiveData<Event<String>>
        get() = _showAlert

    private val _tournamentWasSaved: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val tournamentWasSaved: LiveData<Event<Boolean>>
        get() = _tournamentWasSaved

    var tournamentState: TournamentState = TournamentState.Read
    var activeLayout : ItemPartListBinding? = null
    var target: TargetMy? = null
    var team: TournamentTeam? = null
    var tempFreeParticipantList: ArrayList<Participant> = arrayListOf()
    var tempListParticipant: ArrayList<Participant> = arrayListOf()

    var viewListTargetMy: ArrayList<TargetMy> = arrayListOf()
    var viewListParts: ArrayList<Part> = arrayListOf()
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

    fun addTeam(tournament: Tournament): Tournament{
        val tempArray: ArrayList<TournamentTeam> = tournament.teams as ArrayList<TournamentTeam>
        tempArray.add(TournamentTeam(
            id = UUID.randomUUID().toString(),
            teamName = "Команда №${tournament.teams.size+1}",
            teamNumber = tournament.teams.size+1,
            participants = tempListParticipant
        ))
        tournament.participants = tempFreeParticipantList
        tempListParticipant.clear()
        tournament.teams = tempArray
        return tournament
    }

}