package space.dlsunity.arctour.di

import org.koin.dsl.module
import space.dlsunity.arctour.data.room.repositories.BdDataRepository
import space.dlsunity.arctour.data.room.repositories.TournamentRepository
import space.dlsunity.arctour.data.room.repositories.UserRepository
import space.dlsunity.arctour.domain.usecases.bd.*
import space.dlsunity.arctour.domain.usecases.tournaments.*
import space.dlsunity.arctour.domain.usecases.user.*

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

    //Room Tournament
    factory<GetTournamentByIdUseCase> {
        GetTournamentByIdUseCase(tournamentRepository = get<TournamentRepository>())
    }
    factory<GetAllTournamentsUseCase> {
        GetAllTournamentsUseCase(tournamentRepository = get<TournamentRepository>())
    }
    factory<SaveTournamentUseCase> {
        SaveTournamentUseCase(tournamentRepository = get<TournamentRepository>())
    }
    factory<DeleteTournamentUseCase> {
        DeleteTournamentUseCase(tournamentRepository = get<TournamentRepository>())
    }
    factory<DeleteAllTournamentsUseCase> {
        DeleteAllTournamentsUseCase(tournamentRepository = get<TournamentRepository>())
    }

    //Room User
    factory<GetUserByIdUseCase> {
        GetUserByIdUseCase(userRepository = get<UserRepository>())
    }
    factory<GetAllUsersUseCase> {
        GetAllUsersUseCase(userRepository = get<UserRepository>())
    }
    factory<SaveUserUseCase> {
        SaveUserUseCase(userRepository = get<UserRepository>())
    }
    factory<DeleteUserUseCase> {
        DeleteUserUseCase(userRepository = get<UserRepository>())
    }
    factory<DeleteAllUsersUseCase> {
        DeleteAllUsersUseCase(userRepository = get<UserRepository>())
    }
}
