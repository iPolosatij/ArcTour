package space.dlsunity.arctour.presenter.screens.start_screens

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import space.dlsunity.arctour.domain.usecases.user.GetAllUsersUseCase
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel

class StartViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase
): BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    init {
        navigateToMain()
    }

    private fun navigateToWelcome() {
        viewModelScope.launch {
            delay(3000)
            _navigateCommander.emit(AppDestination.ToWelcome)
        }
    }

    private fun navigateToMain() {
        viewModelScope.launch {
            getAllUsersUseCase.invoke().let {
                if (it.size == 2){
                    _navigateCommander.emit(AppDestination.ToMain)
                }else{
                    navigateToWelcome()
                }
            }
        }
    }
}