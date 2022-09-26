package space.dlsunity.arctour.presenter.screens.main_screen

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

            if (screen == PROFILE || screen == WORKOUT || screen == NOTES || screen == TOURNAMENT) {
                curtain.visibility = View.VISIBLE
            } else {
                curtain.visibility = View.GONE
            }

            toolbar.setOnClickListener {
                when (screen) {
                    //TO_DO back button toolbar actions
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
                    setTitle(WORKOUT)
                    itemFuture.icon = context.getDrawable(R.drawable.ic_bottom_worckout_black)
                }
                R.id.item3 -> {
                    setTitle(TOURNAMENT)
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
                addBtn.setOnClickListener {
                    when (new) {
                        //TO_DO add logic to select a specific action
                    }
                }
            }
        }
    }
    private fun setFragment(id: Int) {
        binding.apply {
            when (id) {
//                R.id.profile -> {
//                    childFragmentManager.beginTransaction()
//                        .replace(fragmentContainer.id, ProfileFragment()).commit()
//                }
//
//                R.id.profile_image -> {
//                    viewModel.changedAvatar = true
//                    activity?.let {
//                        viewModel
//                            .changeProfilePhoto(childFragmentManager, placeHolder.id, requireActivity() as MainActivity) }
//                }
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
                  //  viewModel.setScreen(R.id.profile)
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    companion object {
        const val NOTES = "Notes"
        const val WORKOUT = "Workout"
        const val TOURNAMENT = "Tournament"
        const val PROFILE = "Profile"
    }
}