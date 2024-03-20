package space.dlsunity.simple_crm.presenter.screens.main_container.screens.works

import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.WorksFlagmentBinding
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.simple_crm.presenter.screens.errors.ErrorModel
import space.dlsunity.simple_crm.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.simple_crm.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.simple_crm.utils.extensions.collectWhenStarted

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
                mainContainerViewModel.toCreate()
            }
        }
    }

    private fun closeCardBuilder(){
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
            setScreenTitle(MainContainerFragment.WORKS)
            showAddBtn(false, "")
            showFindBtn(false)
            userEntityDownloaded.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {

                }
            }
        }
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
    }
}