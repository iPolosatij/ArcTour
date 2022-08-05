package space.dlsunity.arctour.domain.usecases

import space.dlsunity.arctour.data.room.data.Bd_data
import space.dlsunity.arctour.data.room.repositories.BdDataRepository

class SaveBdDataUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(chat: Bd_data){
        return bdDataRepository.saveBdEntity(chat)
    }
}