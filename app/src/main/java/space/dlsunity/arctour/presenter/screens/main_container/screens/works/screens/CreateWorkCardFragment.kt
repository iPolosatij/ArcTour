package space.dlsunity.arctour.presenter.screens.main_container.screens.works.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.CreateWorkCardFragmentBinding
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.works.viewmodels.CreateWorkCardViewModel
import space.dlsunity.arctour.utils.extensions.collectWhenStarted

class CreateWorkCardFragment: BaseMvvmFragment<CreateWorkCardViewModel>(R.layout.create_work_card_fragment) {

    private val mainContainerViewModel: MainContainerViewModel by sharedViewModel()
    override val viewModel: CreateWorkCardViewModel by sharedViewModel()
    private val binding: CreateWorkCardFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        observeVM()
        observeContainerVM()
    }

    private fun setUpBinding() {
        binding.apply {

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
            setScreenTitle(requireContext().getString(R.string.creator))
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