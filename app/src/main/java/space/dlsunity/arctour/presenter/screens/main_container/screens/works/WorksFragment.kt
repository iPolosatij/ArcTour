package space.dlsunity.arctour.presenter.screens.main_container.screens.works

import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.WorksFlagmentBinding
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.utils.extensions.collectWhenStarted

class WorksFragment : BaseMvvmFragment<WorksViewModel>(R.layout.works_flagment) {

    private val mainContainerViewModel: MainContainerViewModel by sharedViewModel()
    override val viewModel: WorksViewModel by sharedViewModel()
    private val binding: WorksFlagmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        observeVM()
        observeContainerVM()
    }

    private fun setUpBinding() {
        binding.apply {
            addButtonFrame.addWorkCardButton.setOnClickListener {
                gone(addButtonFrame.root)
                visible(cardBuilder.root)
            }

        }
    }

    private fun closeCardBuilder(){
        binding.apply {
            gone(cardBuilder.root)
        }
    }

    private fun observeVM() {
        viewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
        }
    }

    private fun observeContainerVM() {
        mainContainerViewModel.apply {
            setScreenTitle(MainContainerFragment.WORKS)
            showAddBtn(false, "")
            showFindBtn(false)
            userDownloaded.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {

                }
            }
        }
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
    }
}