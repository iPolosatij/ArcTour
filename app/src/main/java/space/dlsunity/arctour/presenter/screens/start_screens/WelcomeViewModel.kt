package space.dlsunity.arctour.presenter.screens.start_screens

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel

class WelcomeViewModel: BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

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