package space.dlsunity.arctour.presenter.screens.start_screens

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel

class WelcomeViewModel: BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {}

}