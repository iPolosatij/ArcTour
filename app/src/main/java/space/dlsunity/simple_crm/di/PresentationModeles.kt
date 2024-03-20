package space.dlsunity.simple_crm.di

import android.content.Context
import android.content.Intent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.dlsunity.simple_crm.MainActivity
import space.dlsunity.simple_crm.MainViewModel
import space.dlsunity.simple_crm.data.Storages
import space.dlsunity.simple_crm.data.network.StoragesFactory
import space.dlsunity.simple_crm.domain.usecases.users.DeleteAllUsersUseCase
import space.dlsunity.simple_crm.domain.usecases.users.GetAllUserUseCase
import space.dlsunity.simple_crm.domain.usecases.users.SaveUserUseCase
import space.dlsunity.simple_crm.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.hr.HRViewModel
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.lists.ListsViewModel
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.profile.ProfileViewModel
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorksViewModel
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.viewmodels.CreateWorkCardViewModel
import space.dlsunity.simple_crm.presenter.screens.start_screens.StartViewModel
import space.dlsunity.simple_crm.presenter.screens.start_screens.WelcomeViewModel

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
