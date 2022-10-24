package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.databinding.TournamentsListBinding
import space.dlsunity.arctour.presenter.base.adapter.MultiItemsAdapter
import space.dlsunity.arctour.presenter.base.navigation.NavMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragmentDirections
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_container.destinations.MainDestination
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.list.TournamentListItem
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.navigation.navigateSafe
import space.dlsunity.arctour.utils.tools.DialogHelper

class TournamentsListFragment:
    NavMvvmFragment<MainDestination, TournamentsListViewModel>(R.layout.tournaments_list) {

    override val viewModel: TournamentsListViewModel by sharedViewModel()

    private val mainContainerViewModel: MainContainerViewModel by sharedViewModel()

    private val binding: TournamentsListBinding by viewBinding()


    private val tournamentsListAdapter: MultiItemsAdapter by lazy {
        MultiItemsAdapter(
            listOf(
                TournamentListItem(::shortTap, ::longTap)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()
        observeContainerVm()
        setupBinding()
        setUpBackPressed()
    }

    private fun observeVm() {
        viewModel.apply {
            downloadAllTournament()
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)

            showAlert.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { message ->
                    showAlert(message)
                }
            }

            tournamentListDownloaded.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { list ->
                    tournamentsListAdapter.submitList(viewList as List<Tournament>)
                    tournamentsListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun observeContainerVm() {
        mainContainerViewModel.apply {
            setScreenTitle(MainContainerFragment.TOURNAMENTS)
            showAddBtn(true, "")
            needShowBottomMenu(true)
        }
    }

    private fun setupBinding() {
        binding.apply {
            viewModel.apply {
                tournamentsList.adapter = tournamentsListAdapter
                tournamentsListAdapter.submitList(viewList as List<Tournament>)
            }
        }
    }

    override fun handlerDestination(destination: MainDestination) {
        val action: NavDirections? = when (destination) {
            is MainDestination.toWelcome -> {
                MainContainerFragmentDirections.logOut(destination.logout)
            }
            else -> null
        }
        action?.let { findNavController().navigateSafe(it) }
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

            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun shortTap(item: Tournament){}
    private fun longTap(item: Tournament){}
}