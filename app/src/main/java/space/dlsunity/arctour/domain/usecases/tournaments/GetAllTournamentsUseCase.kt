package space.dlsunity.arctour.domain.usecases.tournaments

import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.data.room.repositories.TournamentRepository

class GetAllTournamentsUseCase (
    private val tournamentRepository: TournamentRepository
) {
    suspend operator fun invoke(): List<Tournament>{
        return tournamentRepository.getAllTournaments()
    }
}