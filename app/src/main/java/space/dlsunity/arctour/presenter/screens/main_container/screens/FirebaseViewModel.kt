package space.dlsunity.arctour.presenter.screens.main_container.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import space.dlsunity.arctour.data.network.firebase.FirebaseManager
import space.dlsunity.arctour.data.room.data.Participant
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.utils.auxiliary.Event

class FirebaseViewModel: ViewModel() {

    private val dbManager = FirebaseManager()
    val tournamentData = MutableLiveData<ArrayList<Tournament>>()
    private val _success: MutableLiveData<Event<Boolean>> = MutableLiveData<Event<Boolean>>()
    val success: LiveData<Event<Boolean>>
        get() = _success


    fun publishTournament(tournament: Tournament){
        dbManager.publishTournament(tournament, ::finish)
    }

    fun loadAllTournament(){
        dbManager.getAllTournaments(object: FirebaseManager.ReadDataCallback{
            override fun readData(list: ArrayList<Tournament>) {
                tournamentData.value = list
            }
        })
    }

    fun selectClick(tournament: Tournament, participant: Participant) {
        dbManager.clickSelect(tournament, object : FirebaseManager.FinishWorkListener {
            override fun onFinish() {
                val updatedList = tournamentData.value
                val pos = updatedList?.indexOf(tournament)
                if (pos != -1) {
                    pos?.let { position ->

                        val favCounter =
                            if (tournament.isSelect) {
                                val temp: ArrayList<Participant> = tournament.participants as ArrayList<Participant>
                                temp.add(participant)
                                tournament.participants = temp
                            }
                            else{
                                val temp: ArrayList<Participant> = tournament.participants as ArrayList<Participant>
                                temp.remove(participant)
                                tournament.participants = temp
                            }

                        updatedList[position] = updatedList[position]
                            .copy(
                                isSelect = !tournament.isSelect,
                            )
                    }
                }
                updatedList?.let{tournamentData.postValue(it)}
            }
        })
    }

    fun tournamentViewed(tournament: Tournament){
        dbManager.tournamentViewed(tournament)
    }

    fun loadMyNotice(){
        dbManager.getMyTournaments(object: FirebaseManager.ReadDataCallback{
            override fun readData(list: ArrayList<Tournament>) {
                tournamentData.value = list
            }
        })
    }

    fun loadMyFavoriteTournament(){
        dbManager.getMySelectTournaments(object: FirebaseManager.ReadDataCallback{
            override fun readData(list: ArrayList<Tournament>) {
                tournamentData.value = list
            }
        })
    }

    fun deleteTournament(tournament: Tournament){
        dbManager.deleteTournament(tournament,object : FirebaseManager.FinishWorkListener{
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