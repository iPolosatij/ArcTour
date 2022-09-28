package space.dlsunity.arctour

import androidx.fragment.app.FragmentManager
import space.dlsunity.arctour.presenter.base.BaseActivity
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.auxiliary.FragmentCloseInterface

class MainActivity : BaseActivity(R.layout.activity_main), FragmentCloseInterface {

    override fun handlerProgress(visibility: Boolean) {
    }

    override fun handlerErrors(error: ErrorModel, fragmentManager: FragmentManager) {
    }

}