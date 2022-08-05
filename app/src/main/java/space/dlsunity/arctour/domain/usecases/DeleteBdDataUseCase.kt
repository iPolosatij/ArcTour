package space.dlsunity.arctour.domain.usecases

import space.dlsunity.arctour.data.room.data.Bd_data
import space.dlsunity.arctour.data.room.repositories.BdDataRepository

class DeleteBdDataUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(bdData: Bd_data){
        return bdDataRepository.deleteBdEntity(bdData)
    }
}