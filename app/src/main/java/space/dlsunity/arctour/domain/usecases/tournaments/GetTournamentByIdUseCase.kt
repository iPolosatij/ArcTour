package space.dlsunity.arctour.domain.usecases.tournaments

import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.data.room.repositories.TournamentRepository

class GetTournamentByIdUseCase (
    private val tournamentRepository: TournamentRepository
) {
    suspend operator fun invoke(id: String): Tournament{
       return tournamentRepository.getTournamentById(id)
    }
}