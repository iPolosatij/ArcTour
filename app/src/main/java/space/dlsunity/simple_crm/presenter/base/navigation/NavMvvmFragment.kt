package space.dlsunity.simple_crm.presenter.base.navigation

import androidx.annotation.LayoutRes
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseViewModel

abstract class NavMvvmFragment<D : Destination, VM : BaseViewModel>(@LayoutRes layoutId: Int) :
    BaseMvvmFragment<VM>(layoutId) {

    protected abstract fun handlerDestination(destination: D)
}
