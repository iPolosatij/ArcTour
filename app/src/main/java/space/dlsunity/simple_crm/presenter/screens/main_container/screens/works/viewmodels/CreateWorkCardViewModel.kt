package space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.viewmodels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseViewModel
import space.dlsunity.simple_crm.presenter.screens.errors.ErrorModel
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.destinations.CreateWorkDestination

class CreateWorkCardViewModel(

) : BaseViewModel() {

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()

    private val _destination: MutableSharedFlow<CreateWorkDestination> = MutableSharedFlow()
    val destination: Flow<CreateWorkDestination> = _destination.filterNotNull()

    fun toMain(){
        CoroutineScope(Dispatchers.Default).launch {
            _destination.emit(CreateWorkDestination.ToMain(tab = 1))
        }
    }
}