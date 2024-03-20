package space.dlsunity.simple_crm.di

import org.koin.dsl.module
import space.dlsunity.simple_crm.data.room.repositories.BdDataRepository
import space.dlsunity.simple_crm.data.room.repositories.UserRepository
import space.dlsunity.simple_crm.domain.usecases.DeleteAllBdDataUseCase
import space.dlsunity.simple_crm.domain.usecases.DeleteBdDataUseCase
import space.dlsunity.simple_crm.domain.usecases.GetBdDataByIdUseCase
import space.dlsunity.simple_crm.domain.usecases.GetBdDataUseCase
import space.dlsunity.simple_crm.domain.usecases.SaveBdDataUseCase
import space.dlsunity.simple_crm.domain.usecases.users.DeleteAllUsersUseCase
import space.dlsunity.simple_crm.domain.usecases.users.GetAllUserUseCase
import space.dlsunity.simple_crm.domain.usecases.users.SaveUserUseCase

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
