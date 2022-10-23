package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
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



    var viewListTarget: ArrayList<Tournament>? = null
    var viewListLaps: ArrayList<Tournament>? = null

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

}