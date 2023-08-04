package space.dlsunity.arctour.domain.usecases

import space.dlsunity.arctour.data.room.data.sketch.Bd_data
import space.dlsunity.arctour.data.room.repositories.BdDataRepository

class GetBdDataUseCase(
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(): List<Bd_data> {
        return bdDataRepository.getAllBdEntities()
    }
}