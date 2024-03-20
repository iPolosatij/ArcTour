package space.dlsunity.simple_crm.data.room.repositories.impl

import space.dlsunity.simple_crm.back4app.data.User
import space.dlsunity.simple_crm.data.room.dao.UserDao
import space.dlsunity.simple_crm.data.room.repositories.UserRepository

class UserRepositoryImpl (
    private val userDao: UserDao
): UserRepository {
    override suspend fun getUserEntityById(id: String): User {
        return userDao.findEntityById(id).toUser()
    }

    override suspend fun getAllUserEntities(): List<User> {
        val outList = ArrayList<User>()
        val savedChats = userDao.getAll()
        for (user in savedChats){
            outList.add(user.toUser())
        }
        return outList
    }

    override suspend fun deleteUserEntity(data: User) {
        userDao.delete(data.toEntity())
    }

    override suspend fun saveUserEntity(data: User) {
        userDao.insertAll(data.toEntity())
    }

    override suspend fun deleteAllUserEntities() {
        userDao.deleteAll()
    }
}