package space.dlsunity.simple_crm.data.room.repositories

import space.dlsunity.simple_crm.back4app.data.User

interface UserRepository {
    suspend fun getUserEntityById(id: String): User

    suspend fun getAllUserEntities(): List<User>

    suspend fun deleteUserEntity(entity: User)

    suspend fun saveUserEntity(entity: User)

    suspend fun deleteAllUserEntities()
}