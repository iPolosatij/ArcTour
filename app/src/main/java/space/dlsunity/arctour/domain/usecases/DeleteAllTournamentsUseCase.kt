package space.dlsunity.arctour.domain.usecases

import space.dlsunity.arctour.data.room.repositories.TournamentRepository

class DeleteAllTournamentsUseCase(
    private val tournamentRepository: TournamentRepository
) {
    suspend operator fun invoke(){
        tournamentRepository.getAllTournaments()
    }
}