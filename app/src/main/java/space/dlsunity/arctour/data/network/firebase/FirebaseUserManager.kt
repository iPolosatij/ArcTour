package space.dlsunity.arctour.data.network.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import space.dlsunity.arctour.data.room.data.User

class FirebaseUserManager {
    private val firebaseDb = Firebase.database.getReference(MAIN_NODE)
    private val firebaseStorage = Firebase.storage.getReference(MAIN_NODE)
    private val firebaseAuth = Firebase.auth

    fun publishUser(user: User,
                    finish: (Boolean) -> Unit
    ) {
        if (firebaseAuth.uid != null)
            firebaseDb
                .child(USER_NODE)
                .child(user.memberId)
                .setValue(user).addOnCompleteListener {
                    finish(it.isSuccessful)
                }
    }

    fun publishTrainer(user: User,
                    finish: (Boolean) -> Unit){
        if(firebaseAuth.uid != null)
            firebaseDb
                .child(TRAINER_NODE)
                .child(user.memberId)
                .setValue(user).addOnCompleteListener {
                    finish(it.isSuccessful)
                }
    }

    fun publishAdmin(user: User,
                       finish: (Boolean) -> Unit){
        if(firebaseAuth.uid != null)
            firebaseDb
                .child(ADMIN_NODE)
                .child(user.memberId)
                .setValue(user).addOnCompleteListener {
                    finish(it.isSuccessful)
                }
    }

    private fun removeAdmin(user: User) {
        firebaseAuth.uid?.let { uid ->
            firebaseDb
                .child(ADMIN_NODE)
                .child(user.memberId)
                .removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //Add Event
                    }
                }
        }
    }

    private fun removeUser(user: User) {
        firebaseAuth.uid?.let { uid ->
            firebaseDb
                .child(USER_NODE)
                .child(user.memberId)
                .removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //Add Event
                    }
                }
        }
    }

    private fun removeTrainer(user: User) {
        firebaseAuth.uid?.let { uid ->
            firebaseDb
                .child(TRAINER_NODE)
                .child(user.memberId)
                .removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //Add Event
                    }
                }
        }
    }

    fun getAdmin(user: User){
        val query = firebaseDb.orderByChild(ADMIN_NODE).equalTo(user.memberId)
        readDataFromFirebaseDb(query)
    }

    fun getTrainer(user: User){
        val query = firebaseDb.orderByChild(TRAINER_NODE).equalTo(user.memberId)
        readDataFromFirebaseDb(query)
    }

    private fun readDataFromFirebaseDb(query: Query){
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<User>()
                for(item in snapshot.children){
                    var user: User? = null
                    item.children.forEach{snapShot ->
                        if(user == null) user = snapShot.getValue(User::class.java)
                    }
                }
                //ADD EVENT
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun upLoadImage(byteArray: ByteArray){
        val imStorageReference = firebaseAuth.uid?.let { uid ->
            firebaseStorage.child(uid).child("image_${System.currentTimeMillis()}")
        }
        val uploadTask = imStorageReference?.putBytes(byteArray)
        uploadTask?.continueWithTask { task -> imStorageReference.downloadUrl
        }?.let {
            if (it.isSuccessful){
                //ADD EVENT
            }else{
                //ADD EVENT
            }

        }
    }

    companion object{
        const val USER_NODE = "user"
        const val MAIN_NODE = "main"
        const val ADMIN_NODE = "admin"
        const val TRAINER_NODE = "trainer"
    }
}