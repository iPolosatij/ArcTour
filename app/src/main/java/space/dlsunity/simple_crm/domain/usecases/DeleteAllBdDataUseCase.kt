package space.dlsunity.simple_crm.domain.usecases

import space.dlsunity.simple_crm.data.room.repositories.BdDataRepository

class DeleteAllBdDataUseCase(
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(){
        return bdDataRepository.deleteAllBdEntities()
    }
}