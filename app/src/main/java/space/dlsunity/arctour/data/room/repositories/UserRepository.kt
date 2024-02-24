package space.dlsunity.arctour.data.room.repositories

import space.dlsunity.arctour.back4app.data.User

interface UserRepository {
    suspend fun getUserEntityById(id: String): User

    suspend fun getAllUserEntities(): List<User>

    suspend fun deleteUserEntity(entity: User)

    suspend fun saveUserEntity(entity: User)

    suspend fun deleteAllUserEntities()
}