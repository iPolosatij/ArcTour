package space.dlsunity.simple_crm.presenter.screens.main_container.screens.planing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseViewModel
import space.dlsunity.simple_crm.presenter.screens.errors.ErrorModel

class PlaningViewModel(

) : BaseViewModel() {

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()
}