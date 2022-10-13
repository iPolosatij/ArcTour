package space.dlsunity.arctour.domain.usecases.user

import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.data.room.repositories.UserRepository

class SaveUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User){
        userRepository.saveUser(user)
    }
}