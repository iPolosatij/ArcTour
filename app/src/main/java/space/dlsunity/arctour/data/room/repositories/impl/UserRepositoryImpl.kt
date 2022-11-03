package space.dlsunity.arctour.data.room.repositories.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import space.dlsunity.arctour.data.room.dao.UserDao
import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.data.room.repositories.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {
    override suspend fun getUserById(id: String): User? {
        return withContext(Dispatchers.IO){
            try {
                userDao.findUserById(id)
            }catch (e:Exception){
                null
            }
        }
    }

    override suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO){
            try {
                userDao.getAll()
            }catch (e:Exception){
                listOf()
            }
        }
    }

    override suspend fun deleteUser(user: User) {
        return withContext(Dispatchers.IO) {
            try {
                userDao.delete(user)
            } catch (_: Exception) {

            }
        }
    }

    override suspend fun saveUser(user: User) {
        return withContext(Dispatchers.IO) {
            try {
                userDao.insertAll(user)
            } catch (_: Exception) {

            }
        }
    }

    override suspend fun deleteAllUsers() {
        return withContext(Dispatchers.IO){
            try {
                userDao.deleteAll()
            }catch (_: Exception){

            }
        }
    }
}