package space.dlsunity.simple_crm.presenter.screens.start_screens

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseViewModel
import space.dlsunity.simple_crm.presenter.screens.errors.ErrorModel

class StartViewModel(): BaseViewModel() {

    private val _navigateCommander = MutableSharedFlow<AppDestination>()
    val navigateCommander: SharedFlow<AppDestination> = _navigateCommander.asSharedFlow()

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    private val _changer: MutableSharedFlow<Int> = MutableSharedFlow()
    val changer: Flow<Int> = _changer.filterNotNull()

    var active = true

    init {
        navigateToWelcome()
    }

    private var delayCount = 180L

    fun startChange(){
        CoroutineScope(Dispatchers.Default).launch {
            while (active) {
                if(delayCount > 0) delayCount -= 10
                for (i in 1..2) {
                    delay(delayCount)
                    _changer.emit(i)
                }
            }
        }
    }

    private fun navigateToWelcome() {
        viewModelScope.launch {
            delay(3000)
            _navigateCommander.emit(AppDestination.ToWelcome)
        }
    }
}