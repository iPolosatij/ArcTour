package space.dlsunity.arctour.di

import android.content.Context
import android.content.Intent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.dlsunity.arctour.data.network.StoragesFactory
import space.dlsunity.arctour.domain.usecases.*
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.profile.ProfileViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.TournamentsListViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament.TournamentViewModel
import space.dlsunity.arctour.presenter.screens.start_screens.StartViewModel
import space.dlsunity.arctour.presenter.screens.start_screens.WelcomeViewModel

val presentationModule = module {

    factory<Intent> {
        Intent(get<Context>(), space.dlsunity.arctour.MainActivity::class.java)
    }

    single<space.dlsunity.arctour.data.Storages> {
        StoragesFactory().createNew(get<Context>())
    }

    viewModel {
        space.dlsunity.arctour.MainViewModel()
    }

    viewModel {
        StartViewModel()
    }

    viewModel {
        WelcomeViewModel()
    }

    viewModel {
        MainContainerViewModel(
            localContext = get<Context>(),
            getAllTournamentsUseCase = get<GetAllTournamentsUseCase>(),
            getTournamentByIdUseCase = get<GetTournamentByIdUseCase>(),
            saveTournamentUseCase = get<SaveTournamentUseCase>(),
            deleteAllTournamentsUseCase = get<DeleteAllTournamentsUseCase>(),
            deleteTournamentUseCase = get<DeleteTournamentUseCase>()
        )
    }

    viewModel {
       TournamentsListViewModel(
           localContext = get<Context>(),
           getAllTournamentsUseCase = get<GetAllTournamentsUseCase>(),
           getTournamentByIdUseCase = get<GetTournamentByIdUseCase>(),
           saveTournamentUseCase = get<SaveTournamentUseCase>(),
           deleteAllTournamentsUseCase = get<DeleteAllTournamentsUseCase>(),
           deleteTournamentUseCase = get<DeleteTournamentUseCase>()
        )
    }

    viewModel {
       TournamentViewModel(

        )
    }

    viewModel {
        ProfileViewModel()
    }
}
