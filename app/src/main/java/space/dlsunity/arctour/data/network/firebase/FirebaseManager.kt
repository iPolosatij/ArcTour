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
    private val firebaseDb = Firebase.database.getReference(MAIN_NODE)
    private val firebaseStorage = Firebase.storage.getReference(MAIN_NODE)
    val firebaseAuth = Firebase.auth

    fun publishTournament(tournament: Tournament,
                          finish: (Boolean) -> Unit){
        if(firebaseAuth.uid != null) firebaseDb.child(tournament.tournamentId)
            .child(firebaseAuth.uid!!).child(TOURNAMENT_NODE)
            .setValue(tournament).addOnCompleteListener{
                finish(it.isSuccessful)
            }
    }

    fun tournamentViewed(tournament: Tournament) {
        var counter = tournament.viewsCounter.toInt()
        counter++
        if (firebaseAuth.uid != null) firebaseDb.child(tournament.tournamentId)
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
        firebaseAuth.uid?.let { uid ->
            firebaseDb.child(tournament.tournamentId)
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
        firebaseAuth.uid?.let { uid ->
            firebaseDb.child(tournament.tournamentId)
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
        val query = firebaseDb.orderByChild("/favorite/${firebaseAuth.uid}").equalTo(firebaseAuth.uid)
        readDataFromFirebaseDb(query, readDataCallback)
    }

    fun getMyTournaments(readDataCallback: ReadDataCallback?){
        val query = firebaseDb.orderByChild(firebaseAuth.uid + "/notice/uid").equalTo(firebaseAuth.uid)
        readDataFromFirebaseDb(query, readDataCallback)
    }
    fun getAllTournaments(readDataCallback: ReadDataCallback?){
        val query = firebaseDb.orderByChild(firebaseAuth.uid + "/notice/price")
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
                    val isMyFavorite = firebaseAuth.uid?.let{ uid ->
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
        firebaseDb.child(tournament.tournamentId).child(tournament.uid).removeValue()
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
        val imStorageReference = firebaseAuth.uid?.let { uid ->
            firebaseStorage.child(uid).child("image_${System.currentTimeMillis()}")
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