package space.dlsunity.arctour.domain.usecases

import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.data.room.repositories.TournamentRepository

class SaveTournamentUseCase (
    private val tournamentRepository: TournamentRepository
) {
    suspend operator fun invoke(tournament: Tournament){
        tournamentRepository.saveTournament(tournament)
    }
}