package space.dlsunity.arctour.domain.usecases.bd

import space.dlsunity.arctour.data.room.data.Bd_data
import space.dlsunity.arctour.data.room.repositories.BdDataRepository

class GetBdDataByIdUseCase (
    private val bdDataRepository: BdDataRepository
) {
    suspend operator fun invoke(id: String): Bd_data {
        return bdDataRepository.getBdEntityById(id)
    }
}