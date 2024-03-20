package space.dlsunity.simple_crm.domain.usecases

import space.dlsunity.simple_crm.data.room.data.sketch.Bd_data
import space.dlsunity.simple_crm.data.room.repositories.BdDataRepository

class SaveBdDataUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(chat: Bd_data){
        return bdDataRepository.saveBdEntity(chat)
    }
}