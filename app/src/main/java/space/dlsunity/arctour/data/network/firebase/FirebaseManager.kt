package space.dlsunity.arctour.data.network.firebase

import android.net.Uri
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import space.dlsunity.arctour.data.room.data.ItemInfo
import space.dlsunity.arctour.data.room.data.Tournament

class FirebaseManager {
    val db = Firebase.database.getReference(MAIN_NODE)
    val dbStorage = Firebase.storage.getReference(MAIN_NODE)
    val auth = Firebase.auth

    fun publishTournament(tournament: Tournament, finishListener: FinishWorkListener){
        if(auth.uid != null) db.child(tournament.tournamentId)
            .child(auth.uid!!).child(TOURNAMENT_NODE)
            .setValue(tournament).addOnCompleteListener{
                if(it.isSuccessful) {
                    finishListener.onFinish()
                }
            }
    }

    fun tournamentViewed(tournament: Tournament) {
        var counter = tournament.viewsCounter.toInt()
        counter++
        if (auth.uid != null) db.child(tournament.tournamentId)
            .child(INFO_NODE).setValue(
                ItemInfo(counter.toString(),
                tournament.emails.size.toString(),
                tournament.telephones.size.toString())
            )
    }

    fun clickSelect(tournament: Tournament, finishWorkListener: FinishWorkListener){
        if (tournament.isSelect) removeSelect(tournament, finishWorkListener)
        else addSelect(tournament, finishWorkListener)
    }

    private fun addSelect(tournament: Tournament, finishListener: FinishWorkListener) {
        auth.uid?.let { uid ->
            db.child(tournament.tournamentId)
                .child(SELECT_NODE)
                .child(uid)
                .setValue(uid)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        finishListener.onFinish()
                    }
                }
        }
    }

    private fun removeSelect(tournament: Tournament, finishListener: FinishWorkListener) {
        auth.uid?.let { uid ->
            db.child(tournament.tournamentId)
                .child(SELECT_NODE)
                .child(uid)
                .removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        finishListener.onFinish()
                    }
                }
        }
    }

    fun getMySelectTournaments(readDataCallback: ReadDataCallback?){
        val query = db.orderByChild("/favorite/${auth.uid}").equalTo(auth.uid)
        readDataFromFirebaseDb(query, readDataCallback)
    }

    fun getMyTournaments(readDataCallback: ReadDataCallback?){
        val query = db.orderByChild(auth.uid + "/notice/uid").equalTo(auth.uid)
        readDataFromFirebaseDb(query, readDataCallback)
    }
    fun getAllTournaments(readDataCallback: ReadDataCallback?){
        val query = db.orderByChild(auth.uid + "/notice/price")
        readDataFromFirebaseDb(query, readDataCallback)
    }

    private fun readDataFromFirebaseDb(query: Query, readDataCallback: ReadDataCallback?){
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val noticeList = ArrayList<Tournament>()
                for(item in snapshot.children){
                    var tournament: Tournament? = null
                    item.children.forEach{snapShot ->
                        if(tournament == null) tournament = snapShot.child(TOURNAMENT_NODE).getValue(Tournament::class.java)
                    }

                    val itemInfo = item.child(INFO_NODE).getValue(ItemInfo::class.java)
                    val favCounter = item.child(SELECT_NODE).childrenCount
                    val isMyFavorite = auth.uid?.let{uid ->
                        item.child(SELECT_NODE).child(uid).getValue(String::class.java)
                    }

                    tournament?.apply {
                        isSelect = isMyFavorite != null
                        itemInfo?.let { info ->
                            viewsCounter = info.viewsCounter.toString()
                        }
                    }
                    tournament?.let{ noticeList.add(it) }
                }
                readDataCallback?.readData(noticeList)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun deleteTournament(tournament: Tournament, listener: FinishWorkListener) {
        db.child(tournament.tournamentId).child(tournament.uid).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) listener.onFinish()}
    }

    interface ReadDataCallback {
        fun readData(list: ArrayList<Tournament>)
    }

    interface FinishWorkListener{
        fun onFinish()
    }

    private fun upLoadImage(byteArray: ByteArray, listener: OnCompleteListener<Uri>){
        val imStorageReference = auth.uid?.let { uid ->
            dbStorage.child(uid).child("image_${System.currentTimeMillis()}")
        }
        val uploadTask = imStorageReference?.putBytes(byteArray)
        uploadTask?.continueWithTask { task -> imStorageReference.downloadUrl
        }?.addOnCompleteListener(listener)
    }

    companion object{
        const val TOURNAMENT_NODE = "tournament"
        const val MAIN_NODE = "main"
        const val INFO_NODE = "info"
        const val SELECT_NODE = "select"
    }
}