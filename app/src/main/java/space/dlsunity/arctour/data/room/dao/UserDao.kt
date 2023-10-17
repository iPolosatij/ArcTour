package space.dlsunity.arctour.data.room.dao

import androidx.room.*
import space.dlsunity.arctour.data.room.data.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("DELETE FROM user")
    fun deleteAll()

    @Query("SELECT * FROM user WHERE memberId LIKE :memberId LIMIT 1")
    fun findUserById(memberId: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Delete
    fun delete(tournament: User)
}