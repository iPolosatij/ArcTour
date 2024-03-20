package space.dlsunity.simple_crm

import androidx.fragment.app.FragmentManager
import space.dlsunity.simple_crm.presenter.base.BaseActivity
import space.dlsunity.simple_crm.presenter.screens.errors.ErrorModel
import space.dlsunity.simple_crm.utils.auxiliary.FragmentCloseInterface

class MainActivity : BaseActivity(R.layout.activity_main), FragmentCloseInterface {

    override fun handlerProgress(visibility: Boolean) {
    }

    override fun handlerErrors(error: ErrorModel, fragmentManager: FragmentManager) {
    }

}