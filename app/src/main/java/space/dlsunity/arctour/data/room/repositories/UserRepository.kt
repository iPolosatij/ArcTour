package space.dlsunity.arctour.data.room.repositories

import space.dlsunity.arctour.data.room.data.User

interface UserRepository {

    suspend fun getUserById(id: String): User?

    suspend fun getAllUsers(): List<User>

    suspend fun deleteUser(user: User)

    suspend fun saveUser(user: User)

    suspend fun deleteAllUsers()

}