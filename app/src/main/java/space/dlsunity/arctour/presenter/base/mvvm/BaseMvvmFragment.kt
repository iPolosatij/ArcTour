package space.dlsunity.arctour.presenter.base.mvvm

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import space.dlsunity.arctour.domain.model.exception.NetworkException
import space.dlsunity.arctour.presenter.base.BaseActivity
import space.dlsunity.arctour.presenter.base.BaseFragment
import space.dlsunity.arctour.utils.extensions.collectWhenStarted


abstract class BaseMvvmFragment<VM : BaseViewModel>(@LayoutRes layoutId: Int, private val observeProgress: Boolean = true, private val observeError: Boolean = true) : BaseFragment(layoutId) {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeBaseVm()
    }

    private fun observeBaseVm() {
        viewModel.apply {
            progressEvent.collectWhenStarted(viewLifecycleOwner) {
                if (observeProgress) {
                    (requireActivity() as BaseActivity).handlerProgress(it)
                }
            }

            errorEvent.collectWhenStarted(viewLifecycleOwner) {
                if (it.exception !is NetworkException.Unauthorized && observeError) {
                    (requireActivity() as BaseActivity).handlerErrors(it, childFragmentManager)
                }
            }
        }
    }
}