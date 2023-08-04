package space.dlsunity.arctour.data.room.repositories.impl

import space.dlsunity.arctour.data.room.dao.BdDao
import space.dlsunity.arctour.data.room.data.sketch.Bd_data
import space.dlsunity.arctour.data.room.data.sketch.toData
import space.dlsunity.arctour.data.room.data.sketch.toEntity
import space.dlsunity.arctour.data.room.repositories.BdDataRepository

class BdDataRepositoryImpl(
    private val bdDao: BdDao
): BdDataRepository {
    override suspend fun getBdEntityById(id: String): Bd_data {
        return bdDao.findBdEntityById(id).toData()
    }

    override suspend fun getAllBdEntities(): List<Bd_data> {
        val outList = ArrayList<Bd_data>()
        val savedChats = bdDao.getAll()
        for (chatsEntity in savedChats){
            outList.add(chatsEntity.toData())
        }
        return outList
    }

    override suspend fun deleteBdEntity(data: Bd_data) {
        bdDao.delete(data.toEntity())
    }

    override suspend fun saveBdEntity(data: Bd_data) {
        bdDao.insertAll(data.toEntity())
    }

    override suspend fun deleteAllBdEntities() {
        bdDao.deleteAll()
    }
}