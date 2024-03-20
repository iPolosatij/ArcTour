package space.dlsunity.simple_crm.domain.usecases

import space.dlsunity.simple_crm.data.room.data.sketch.Bd_data
import space.dlsunity.simple_crm.data.room.repositories.BdDataRepository

class DeleteBdDataUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(bdData: Bd_data){
        return bdDataRepository.deleteBdEntity(bdData)
    }
}