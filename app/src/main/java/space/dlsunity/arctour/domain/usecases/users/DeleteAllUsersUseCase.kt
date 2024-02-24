package space.dlsunity.arctour.domain.usecases.users

import space.dlsunity.arctour.data.room.repositories.UserRepository

class DeleteAllUsersUseCase (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.deleteAllUserEntities()

}