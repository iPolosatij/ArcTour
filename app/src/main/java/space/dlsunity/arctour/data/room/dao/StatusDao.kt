package space.dlsunity.arctour.data.room.dao

import androidx.room.*
import space.dlsunity.arctour.data.room.data.Status

@Dao
interface StatusDao {
    @Query("SELECT * FROM status_table")
    fun getAll(): List<Status>

    @Query("DELETE FROM status_table")
    fun deleteAll()

    @Query("SELECT * FROM status_table WHERE id LIKE :id LIMIT 1")
    fun findEntityById(id: String): Status

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: Status)

    @Delete
    fun delete(entity: Status)
}