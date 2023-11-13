package space.dlsunity.arctour.presenter.screens.main_container.screens.works.viewmodels

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel

class CreateWorkCardViewModel(

) : BaseViewModel() {

    private val _error: MutableSharedFlow<ErrorModel> = MutableSharedFlow()
    val error: Flow<ErrorModel> = _error.filterNotNull()
}