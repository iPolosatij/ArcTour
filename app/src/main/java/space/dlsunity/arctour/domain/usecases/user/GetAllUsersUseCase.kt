package space.dlsunity.arctour.domain.usecases.user

import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.data.room.repositories.UserRepository

class GetAllUsersUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<User>{
        return userRepository.getAllUsers()
    }
}