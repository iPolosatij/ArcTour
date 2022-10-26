package space.dlsunity.arctour.presenter.screens.main_container

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.get
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.MainContainerFragmentBinding
import space.dlsunity.arctour.presenter.base.navigation.NavMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.destinations.MainDestination
import space.dlsunity.arctour.presenter.screens.main_container.screens.profile.ProfileFragment
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.TournamentsListFragment
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create.CreateTournamentFragment
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.navigation.navigateSafe
import space.dlsunity.arctour.utils.tools.DialogHelper
import space.dlsunity.arctour.utils.tools.ImageManager

class MainContainerFragment :
    NavMvvmFragment<MainDestination, MainContainerViewModel>(R.layout.main_container_fragment) {

    override val viewModel: MainContainerViewModel by sharedViewModel()

    private val binding: MainContainerFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomMenu()
        observeVm()
        setupBinding()
        setUpBackPressed()
    }

    private fun setupNavAppBar(screen: String) {
        binding.apply {

            if (screen == PROFILE || screen == WORKOUTS || screen == NOTES || screen == TOURNAMENTS) {
                curtain.visibility = View.VISIBLE
            } else {
                curtain.visibility = View.GONE
            }
            if (screen == WORKOUTS || screen == NOTES || screen == TOURNAMENTS) {
                addBtn.visibility = View.VISIBLE

                addBtn.setOnClickListener {
                    if (screen == TOURNAMENTS) {
                        viewModel.setScreen(R.id.create_tournament)
                    }
                    if (screen == NOTES) { }
                    if (screen == WORKOUTS) { }
                }
            }else{
                addBtn.visibility = View.GONE
            }
            toolbar.setOnClickListener {
                when (screen) {
                    CREATE_TOURNAMENT ->{
                        viewModel.setScreen(R.id.item3)
                    }
                }
            }
        }
    }

    private fun setupBottomMenu() {
        binding.apply {
            binding.bottomNavMenu.itemIconTintList = null
            bottomNavMenu.setOnItemSelectedListener {
                changeIcons(it, viewModel.menuItemActual)
                viewModel.menuItemActual = it
                viewModel.setScreen(it.itemId)
                true
            }
            bottomNavMenu.selectedItemId = R.id.item4
        }
    }

    private fun changeIcons(itemFuture: MenuItem?, itemActual: MenuItem?) {
        binding.bottomNavMenu.apply {

            when (itemActual?.itemId) {
                R.id.item1 -> {
                    itemActual.icon = context.getDrawable(R.drawable.ic_bottom_note_gray)
                }
                R.id.item2 -> {
                    itemActual.icon = context.getDrawable(R.drawable.ic_bottom_workout_gray)
                }
                R.id.item3 -> {
                    itemActual.icon = context.getDrawable(R.drawable.ic_bottom_tournament_gray)
                }
                R.id.item4 -> {
                    if (viewModel.photoMain == viewModel.defaultPhotoProfile)
                        itemActual.icon = context.getDrawable(R.drawable.ic_bottom_profile_gray)
                }
            }

            when (itemFuture?.itemId) {
                R.id.item1 -> {
                    setTitle(NOTES)
                    itemFuture.icon = context.getDrawable(R.drawable.ic_bottom_note_black)
                }
                R.id.item2 -> {
                    setTitle(WORKOUTS)
                    itemFuture.icon = context.getDrawable(R.drawable.ic_bottom_workout_black)
                }
                R.id.item3 -> {
                    setTitle(TOURNAMENTS)
                    itemFuture.icon = context.getDrawable(R.drawable.ic_bottom_tournament_black)
                }
                R.id.item4 -> {
                    setTitle(PROFILE)
                    if (viewModel.photoMain == viewModel.defaultPhotoProfile)
                        itemFuture.icon = context.getDrawable(R.drawable.ic_bottom_profile_black)
                }
            }
        }
    }

    private fun saveUser() {
        viewModel.saveUser()
    }

    private fun observeVm() {
        viewModel.apply {
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            downloadUser()
            needShowBottomMenu.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { needShow ->
                    setShowBottomMenu(needShow)
                }
            }
            logOut.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    toWelcome()
                }
            }
            needAddBtn.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { needShow ->
                    needShowAddBtn(needShow)
                }
            }
            needFindBtn.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { needShow ->
                    needShowFindBtn(needShow)
                }
            }
            needLoader.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { needShow ->
                    needShowLoader(needShow)
                }
            }
            showAlert.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { message ->
                    showAlert(message)
                }
            }
            title.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { screen ->
                    setTitle(screen)
                }
            }
            activeScreen.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { id ->
                    setFragment(id)
                }
            }
            icProfilePhoto.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { bitmap ->
                    binding.bottomNavMenu.menu[3].icon =
                        ImageManager.getBitmapToDrawable(bitmap, requireContext())
                }
            }
        }
    }

    private fun needShowAddBtn(needShow: Boolean) {
        binding.apply {
            if (needShow) addBtn.visibility = View.VISIBLE
            else addBtn.visibility = View.GONE
        }
    }

    private fun needShowFindBtn(needShow: Boolean) {
        binding.apply {
            if (needShow) findBtn.visibility = View.VISIBLE
            else findBtn.visibility = View.GONE
        }
    }

    private fun needShowLoader(needShow: Boolean) {
        binding.apply {
            if (needShow) loader.visibility = View.VISIBLE
            else {
                viewModel.loaderMessage = ""
                loader.visibility = View.GONE
            }
            loadMessage.text = viewModel.loaderMessage
        }
    }

    private fun setShowBottomMenu(needShow: Boolean) {
        binding.apply {
            if (needShow) {
                bottomNavMenu.visibility = View.VISIBLE
            } else {
                bottomNavMenu.visibility = View.GONE
            }
        }
    }

    private fun setupBinding() {
        binding.apply {
            viewModel.apply {

            }
        }
    }
    private fun setFragment(id: Int) {
        binding.apply {
            when (id) {
                R.id.item4 -> {
                    childFragmentManager.beginTransaction()
                        .replace(fragmentContainer.id, ProfileFragment()).commit()
                }
                R.id.item3 -> {
                    childFragmentManager.beginTransaction()
                        .replace(fragmentContainer.id, TournamentsListFragment()).commit()
                }
                R.id.item2 -> {

                }
                R.id.item1 -> {

                }
                R.id.create_tournament-> {
                    childFragmentManager.beginTransaction()
                        .replace(fragmentContainer.id, CreateTournamentFragment()).commit()
                }
            }
        }
    }

    private fun setTitle(screen: String) {
        binding.apply {
            setupNavAppBar(screen)
            toolbar.title = screen
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
                if (viewModel.changedAvatar) {
                    childFragmentManager.fragments.forEach { fragment ->
                        if (fragment.isVisible)
                            childFragmentManager
                                .beginTransaction()
                                .remove(fragment)
                                .commit()
                    }
                    viewModel.changedAvatar = false
                  //viewModel.setScreen(R.id.profile)
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {
        const val NOTES = "Notes"
        const val WORKOUTS = "Workouts"
        const val TOURNAMENTS = "Tournaments"
        const val PROFILE = "Profile"
        const val CREATE_TOURNAMENT = "Создать турнир"

        //
        const val TOURNAMENT = "Tournament"
        const val WORKOUT = "Workout"
    }
}