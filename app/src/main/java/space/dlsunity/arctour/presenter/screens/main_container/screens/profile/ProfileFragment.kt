package space.dlsunity.arctour.presenter.screens.main_container.screens.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.ProfileFragmentBinding
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.extensions.toByteArray

class ProfileFragment : BaseMvvmFragment<ProfileViewModel>(R.layout.profile_fragment) {

    private val mainContainerViewModel: MainContainerViewModel by sharedViewModel()
    override val viewModel: ProfileViewModel by sharedViewModel()
    private val binding: ProfileFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFields()
        setUpBinding()
        observeVM()
        observeContainerVM()
    }

    private fun setUpBinding() {
        binding.apply {

            mainContainerViewModel.photoMain?.let { bitmap ->
                if (bitmap == mainContainerViewModel.defaultPhotoProfile) {
                    profileImage.setImageBitmap(bitmap)
                } else {
                    profileImage.setImageBitmap(bitmap)
                    mainContainerViewModel.setBottomMenuIc(bitmap)
                }
            }

            nameClick.setOnClickListener {
                mainContainerViewModel.setScreen(0)
            }

            lastnameClick.setOnClickListener {
                mainContainerViewModel.setScreen(0)
            }

            phoneClick.setOnClickListener {
                mainContainerViewModel.setScreen(0)
            }

            emailClick.setOnClickListener {
                mainContainerViewModel.setScreen(0)
            }

            addPhoto.setOnClickListener {
                    mainContainerViewModel.setScreen(R.id.profile_image)
            }
        }
    }

    private fun observeVM() {
        viewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            needSaveUser.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    mainContainerViewModel.user?.let { it1 -> saveUser(it1) }
                    updateFields()
                }
            }
        }
    }

    private fun observeContainerVM() {
        mainContainerViewModel.apply {
            setScreenTitle(MainContainerFragment.PROFILE)
            showAddBtn(false, "")
            showFindBtn(false)
            userDownloaded.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    updateFields()
                }
            }
            needUpdateProfilePhoto.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    binding.profileImage.setImageBitmap(mainContainerViewModel.photoMain)
                }
            }

            profilePhoto.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { bitmap ->
                    changedAvatar = false
                    photoMain = bitmap
                    viewModel.vCard?.avatar = bitmap.toByteArray()
                    user?.photo = bitmap.toByteArray()
                    viewModel.needSaveUser()
                    saveUser()
                    setScreen(R.id.profile)
                }
            }
        }
    }

    private fun updateFields() {
        binding.apply {
            mainContainerViewModel.user.let { user ->
                viewModel.apply {
                    user?.photo?.let { mainContainerViewModel.setProfilePhoto(it) }
                    user?.name?.let {
                        nameValue.text = it
                    }
                    user?.phone?.let {
                        phoneValue.text = it
                    }
                    user?.email?.let {
                        emailValue.text = it
                    }
                }
            }
        }
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
    }
}