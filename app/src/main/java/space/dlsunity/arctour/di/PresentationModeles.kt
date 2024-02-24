package space.dlsunity.arctour.di

import android.content.Context
import android.content.Intent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.dlsunity.arctour.MainActivity
import space.dlsunity.arctour.MainViewModel
import space.dlsunity.arctour.data.Storages
import space.dlsunity.arctour.data.network.StoragesFactory
import space.dlsunity.arctour.domain.usecases.users.DeleteAllUsersUseCase
import space.dlsunity.arctour.domain.usecases.users.GetAllUserUseCase
import space.dlsunity.arctour.domain.usecases.users.SaveUserUseCase
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.hr.HRViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.lists.ListsViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.profile.ProfileViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.works.WorksViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.works.viewmodels.CreateWorkCardViewModel
import space.dlsunity.arctour.presenter.screens.start_screens.StartViewModel
import space.dlsunity.arctour.presenter.screens.start_screens.WelcomeViewModel

val presentationModule = module {

    factory<Intent> {
        Intent(get<Context>(), MainActivity::class.java)
    }

    single<Storages> {
        StoragesFactory().createNew(get<Context>())
    }

    viewModel {
        MainViewModel()
    }

    viewModel {
        StartViewModel()
    }

    viewModel {
        WelcomeViewModel(
            saveUserUseCase = get<SaveUserUseCase>(),
            getAllUserUseCase = get<GetAllUserUseCase>(),
        )
    }

    viewModel {
        MainContainerViewModel(
            localContext = get<Context>()
        )
    }

    viewModel {
        ProfileViewModel(
            deleteUsersUseCase = get<DeleteAllUsersUseCase>()
        )
    }

    viewModel {
        HRViewModel()
    }

    viewModel {
        ListsViewModel()
    }

    viewModel {
        WorksViewModel()
    }

    viewModel {
        CreateWorkCardViewModel()
    }
}
