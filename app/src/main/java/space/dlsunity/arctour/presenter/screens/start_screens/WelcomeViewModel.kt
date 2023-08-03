package space.dlsunity.arctour.presenter.screens.start_screens

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel

class WelcomeViewModel: BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {}

    fun toMain(){
        viewModelScope.launch {
            _navigateCommander.emit(AppDestination.ToMain)
        }
    }

}