package space.dlsunity.arctour.data.room.repositories.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import space.dlsunity.arctour.data.room.dao.TournamentDao
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.data.room.repositories.TournamentRepository

class TournamentRepositoryImpl(
    private val tournamentDao: TournamentDao
): TournamentRepository {
    override suspend fun getTournamentById(id: String): Tournament? {
        return withContext(Dispatchers.IO) {
            try {
                tournamentDao.findTournamentById(id)
            } catch (_: Exception){
               null
            }
        }
    }

    override suspend fun getAllTournaments(): List<Tournament> {
        return withContext(Dispatchers.IO) {
            try {
                tournamentDao.getAll()
            } catch (_: Exception) {
                listOf<Tournament>()
            }
        }

    }

    override suspend fun deleteTournament(tournament: Tournament) {
        return withContext(Dispatchers.IO) {
            try {
                tournamentDao.delete(tournament)
            } catch (_: Exception) {

            }
        }
    }

    override suspend fun saveTournament(tournament: Tournament) {
        return withContext(Dispatchers.IO) {
            try {
                tournamentDao.insertAll(tournament)
            } catch (_: Exception) {

            }
        }
    }

    override suspend fun deleteAllTournaments() {
        return withContext(Dispatchers.IO) {
            try {
                tournamentDao.deleteAll()
            } catch (_: Exception) {

            }
        }
    }
}