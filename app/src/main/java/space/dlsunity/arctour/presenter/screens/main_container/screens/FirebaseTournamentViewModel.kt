package space.dlsunity.arctour.presenter.screens.main_container.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.dlsunity.arctour.data.network.firebase.FirebaseTournamentManager
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.utils.auxiliary.Event

class FirebaseTournamentViewModel: ViewModel() {

    private val dbManager = FirebaseTournamentManager()
    val tournamentData = MutableLiveData<ArrayList<Tournament>>()
    private val _success: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>>
        get() = _success


    fun publishTournament(tournament: Tournament){
        dbManager.publishTournament(tournament, ::finish)
    }

    fun loadAllTournament(){
        dbManager.getAllTournaments(object: FirebaseTournamentManager.ReadDataCallback{
            override fun readData(list: ArrayList<Tournament>) {
                tournamentData.value = list
            }
        })
    }

    fun selectClick(tournament: Tournament) {
        dbManager.clickSelect(tournament)
    }

    fun tournamentViewed(tournament: Tournament){
        dbManager.tournamentViewed(tournament)
    }

    fun loadMyNotice(){
        dbManager.getMyTournaments(object: FirebaseTournamentManager.ReadDataCallback{
            override fun readData(list: ArrayList<Tournament>) {
                tournamentData.value = list
            }
        })
    }

    fun loadMyFavoriteTournament(){
        dbManager.getMySelectTournaments(object: FirebaseTournamentManager.ReadDataCallback{
            override fun readData(list: ArrayList<Tournament>) {
                tournamentData.value = list
            }
        })
    }

    fun deleteTournament(tournament: Tournament){
        dbManager.deleteTournament(tournament,object : FirebaseTournamentManager.FinishWorkListener{
            override fun onFinish() {
                val updatedList = tournamentData.value
                updatedList?.remove(tournament)
                updatedList?.let{tournamentData.postValue(it)}
            }
        })
    }

    private fun finish(success: Boolean){
        _success.postValue(Event(success))
    }
}