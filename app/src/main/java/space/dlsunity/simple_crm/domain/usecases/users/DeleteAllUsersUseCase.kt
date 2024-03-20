package space.dlsunity.simple_crm.domain.usecases.users

import space.dlsunity.simple_crm.data.room.repositories.UserRepository

class DeleteAllUsersUseCase (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.deleteAllUserEntities()

}