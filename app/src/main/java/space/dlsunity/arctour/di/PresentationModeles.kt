package space.dlsunity.arctour.di

import android.content.Context
import android.content.Intent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.dlsunity.arctour.data.network.StoragesFactory
import space.dlsunity.arctour.presenter.screens.main_screen.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_screen.screens.ProfileViewModel
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
            localContext = get<Context>()
        )
    }

    viewModel {
        ProfileViewModel()
    }
}
