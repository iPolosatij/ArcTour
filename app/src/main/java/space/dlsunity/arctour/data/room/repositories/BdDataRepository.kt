package space.dlsunity.arctour.data.room.repositories

import space.dlsunity.arctour.data.room.data.sketch.Bd_data

interface BdDataRepository {

    suspend fun getBdEntityById(id: String): Bd_data

    suspend fun getAllBdEntities(): List<Bd_data>

    suspend fun deleteBdEntity(entity: Bd_data)

    suspend fun saveBdEntity(entity: Bd_data)

    suspend fun deleteAllBdEntities()

}