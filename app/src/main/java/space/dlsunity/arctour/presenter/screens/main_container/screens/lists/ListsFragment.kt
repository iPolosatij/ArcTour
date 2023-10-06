package space.dlsunity.arctour.presenter.screens.main_container.screens.lists

import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.ListsFragmentBinding
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.utils.extensions.collectWhenStarted

class ListsFragment : BaseMvvmFragment<ListsViewModel>(R.layout.lists_fragment) {

    private val mainContainerViewModel: MainContainerViewModel by sharedViewModel()
    override val viewModel: ListsViewModel by sharedViewModel()
    private val binding: ListsFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFields()
        setUpBinding()
        observeVM()
        observeContainerVM()
    }

    private fun setUpBinding() {
        binding.apply {

        }
    }

    private fun observeVM() {
        viewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
        }
    }

    private fun observeContainerVM() {
        mainContainerViewModel.apply {
            setScreenTitle(MainContainerFragment.LISTS)
            showAddBtn(false, "")
            showFindBtn(false)
            userDownloaded.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    updateFields()
                }
            }
        }
    }

    private fun updateFields() {
        binding.apply {
            mainContainerViewModel.user.let { user ->
                viewModel.apply {

                }
            }
        }
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
    }
}