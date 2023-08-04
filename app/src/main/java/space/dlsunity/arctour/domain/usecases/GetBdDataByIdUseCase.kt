package space.dlsunity.arctour.domain.usecases

import space.dlsunity.arctour.data.room.data.sketch.Bd_data
import space.dlsunity.arctour.data.room.repositories.BdDataRepository

class GetBdDataByIdUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(id: String): Bd_data {
        return bdDataRepository.getBdEntityById(id)
    }
}