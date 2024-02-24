package space.dlsunity.arctour.domain.usecases.users

import space.dlsunity.arctour.back4app.data.User
import space.dlsunity.arctour.data.room.repositories.UserRepository

class SaveUserUseCase (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User) =  userRepository.saveUserEntity(user)
}