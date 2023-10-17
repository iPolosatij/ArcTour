package space.dlsunity.arctour.domain.usecases.user

import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.data.room.repositories.UserRepository

class DeleteUserUseCase (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User){
        userRepository.deleteUser(user)
    }
}