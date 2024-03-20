package space.dlsunity.simple_crm.domain.usecases.users

import space.dlsunity.simple_crm.back4app.data.User
import space.dlsunity.simple_crm.data.room.repositories.UserRepository

class GetAllUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): List<User> = userRepository.getAllUserEntities()
}