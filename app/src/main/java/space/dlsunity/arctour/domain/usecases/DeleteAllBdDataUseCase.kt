package space.dlsunity.arctour.domain.usecases

import space.dlsunity.arctour.data.room.repositories.BdDataRepository

class DeleteAllBdDataUseCase(
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(){
        return bdDataRepository.deleteAllBdEntities()
    }
}