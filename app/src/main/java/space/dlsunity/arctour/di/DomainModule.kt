package space.dlsunity.arctour.di

import org.koin.dsl.module
import space.dlsunity.arctour.data.room.repositories.BdDataRepository
import space.dlsunity.arctour.data.room.repositories.UserRepository
import space.dlsunity.arctour.domain.usecases.DeleteAllBdDataUseCase
import space.dlsunity.arctour.domain.usecases.DeleteBdDataUseCase
import space.dlsunity.arctour.domain.usecases.GetBdDataByIdUseCase
import space.dlsunity.arctour.domain.usecases.GetBdDataUseCase
import space.dlsunity.arctour.domain.usecases.SaveBdDataUseCase
import space.dlsunity.arctour.domain.usecases.users.DeleteAllUsersUseCase
import space.dlsunity.arctour.domain.usecases.users.GetAllUserUseCase
import space.dlsunity.arctour.domain.usecases.users.SaveUserUseCase

val domainModule = module {

    //Room bdData
    factory<GetBdDataByIdUseCase> {
        GetBdDataByIdUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<GetBdDataUseCase> {
        GetBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<SaveBdDataUseCase> {
        SaveBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<DeleteBdDataUseCase> {
        DeleteBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }
    factory<DeleteAllBdDataUseCase> {
        DeleteAllBdDataUseCase(bdDataRepository = get<BdDataRepository>())
    }

    //Room User Use Cases

    factory<GetAllUserUseCase> {
        GetAllUserUseCase(userRepository = get<UserRepository>())
    }
    factory<SaveUserUseCase> {
        SaveUserUseCase(userRepository = get<UserRepository>())
    }
    factory<DeleteAllUsersUseCase> {
        DeleteAllUsersUseCase(userRepository = get<UserRepository>())
    }


}
