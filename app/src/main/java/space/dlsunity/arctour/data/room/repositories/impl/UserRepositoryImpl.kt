package space.dlsunity.arctour.data.room.repositories.impl

import space.dlsunity.arctour.data.room.dao.UserDao
import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.data.room.repositories.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {
    override suspend fun getUserById(id: String): User {
        return userDao.findUserById(id)
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAll()
    }

    override suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

    override suspend fun saveUser(user: User) {
       userDao.insertAll(user)
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAll()
    }
}