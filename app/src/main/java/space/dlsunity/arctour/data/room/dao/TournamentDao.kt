package space.dlsunity.arctour.data.room.dao

import androidx.room.*
import space.dlsunity.arctour.data.room.data.Tournament

@Dao
interface TournamentDao {
    @Query("SELECT * FROM tournament")
    fun getAll(): List<Tournament>

    @Query("DELETE FROM tournament")
    fun deleteAll()

    @Query("SELECT * FROM tournament WHERE tournamentId LIKE :tournamentId LIMIT 1")
    fun findTournamentById(tournamentId: String): Tournament

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tournament: Tournament)

    @Delete
    fun delete(tournament: Tournament)
}