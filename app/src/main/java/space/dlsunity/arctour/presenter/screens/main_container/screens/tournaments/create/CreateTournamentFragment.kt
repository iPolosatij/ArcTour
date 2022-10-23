package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.databinding.CreateTournamentFragmentBinding
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.tools.DialogHelper

class CreateTournamentFragment : BaseMvvmFragment<CreateTournamentViewModel>(R.layout.create_tournament_fragment) {

    override val viewModel: CreateTournamentViewModel by sharedViewModel()
    val containerViewModel: MainContainerViewModel by sharedViewModel()

    private val binding: CreateTournamentFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()
        observeContainerVm()
        setupBinding()
        setUpBackPressed()
    }

    private fun observeVm() {
        viewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            showAlert.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { message ->
                    showAlert(message)
                }
            }
        }
    }

    private fun observeContainerVm() {
        containerViewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            setScreenTitle(MainContainerFragment.CREATE_TOURNAMENT)
            needShowBottomMenu(false)
            showAddBtn(false, "")
        }
    }

    private fun setupBinding() {
        binding.apply {
            viewModel.apply {

            }
        }
    }

    private fun showAlert(message: String) {
        DialogHelper.createClosingDialog(requireActivity(), message)
        hideKeyboard()
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
        showAlert(ex.exception.stackTraceToString())
    }

    private fun setUpBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                containerViewModel.setScreen(R.id.item3)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun shortTap(item: Tournament){}
    private fun longTap(item: Tournament){}

}