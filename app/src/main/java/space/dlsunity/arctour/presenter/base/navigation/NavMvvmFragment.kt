package space.dlsunity.arctour.presenter.base.navigation

import androidx.annotation.LayoutRes
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.base.mvvm.BaseViewModel

abstract class NavMvvmFragment<D : Destination, VM : BaseViewModel>(@LayoutRes layoutId: Int) :
    BaseMvvmFragment<VM>(layoutId) {

    protected abstract fun handlerDestination(destination: D)
}
