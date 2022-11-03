package space.dlsunity.arctour.data.room.repositories

import space.dlsunity.arctour.data.room.data.Tournament

interface TournamentRepository {

    suspend fun getTournamentById(id: String): Tournament?

    suspend fun getAllTournaments(): List<Tournament>

    suspend fun deleteTournament(tournament: Tournament)

    suspend fun saveTournament(tournament: Tournament)

    suspend fun deleteAllTournaments()

}