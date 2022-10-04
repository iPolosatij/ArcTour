package space.dlsunity.arctour.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.dlsunity.arctour.data.room.data.BdEntity
import space.dlsunity.arctour.data.room.data.Tournament

interface TournamentDao {
    @Query("SELECT * FROM tournament")
    fun getAll(): List<BdEntity>

    @Query("DELETE FROM tournament")
    fun deleteAll()

    @Query("SELECT * FROM tournament WHERE tournamentId LIKE :tournamentId LIMIT 1")
    fun findBdEntityById(tournamentId: String): BdEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tournament: Tournament)

    @Delete
    fun delete(chat: BdEntity)
}