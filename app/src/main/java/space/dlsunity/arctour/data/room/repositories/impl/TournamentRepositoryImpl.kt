package space.dlsunity.arctour.data.room.repositories.impl

import space.dlsunity.arctour.data.room.dao.TournamentDao
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.data.room.repositories.TournamentRepository

class TournamentRepositoryImpl(
    private val tournamentDao: TournamentDao
): TournamentRepository {
    override suspend fun getTournamentById(id: String): Tournament {
        return tournamentDao.findTournamentById(id)
    }

    override suspend fun getAllTournaments(): List<Tournament> {
        return tournamentDao.getAll()
    }

    override suspend fun deleteTournament(tournament: Tournament) {
        tournamentDao.delete(tournament)
    }

    override suspend fun saveTournament(tournament: Tournament) {
        tournamentDao.insertAll(tournament)
    }

    override suspend fun deleteAllTournaments() {
        tournamentDao.deleteAll()
    }
}