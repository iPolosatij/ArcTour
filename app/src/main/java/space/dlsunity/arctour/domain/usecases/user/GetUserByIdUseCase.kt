package space.dlsunity.arctour.domain.usecases.user

import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.data.room.repositories.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String): User?{
        return userRepository.getUserById(id)
    }
}