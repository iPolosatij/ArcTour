package space.dlsunity.arctour.presenter.screens.main_container.screens.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
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
import java.util.*

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

            addPhoto.setOnClickListener {
                    mainContainerViewModel.setScreen(R.id.profile_image)
            }

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.class_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                classValue.adapter = adapter
            }
            editSave.setOnClickListener {
                if(viewModel.mode == Mode.Read){
                    editSave.text = "Save"
                    fieldContainer.background = requireContext().getDrawable(R.color.lite_red)
                    editSave.background = requireContext().getDrawable(R.drawable.white_action_button)
                    logOut.visibility = View.GONE
                    viewModel.mode = Mode.Edit
                    nameSafety.isClickable = false
                    lastnameSafety.isClickable = false
                    nickSafety.isClickable = false
                    classSafety.isClickable = false
                    emailSafety.isClickable = false
                    phoneSafety.isClickable = false
                    bornDateSafety.isClickable = false
                    phoneSafety.isClickable = false
                }else{
                    mainContainerViewModel.user?.apply {
                        memberId = UUID.randomUUID().toString()
                        name = nameValue.text.toString()
                        last_name = lastnameValue.text.toString()
                        nick = nickValue.text.toString()
                        phone = phoneValue.text.toString()
                        email = emailValue.text.toString()
                        viewModel.saveUser(this)
                    }
                }
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

            userWasSaved.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    binding.apply {
                        editSave.text = "Edit profile"
                        fieldContainer.background = requireContext().getDrawable(R.color.white)
                        editSave.background = requireContext().getDrawable(R.drawable.black_action_button)
                        logOut.visibility = View.VISIBLE
                        viewModel.mode = Mode.Read
                        nameSafety.isClickable = true
                        lastnameSafety.isClickable = true
                        nickSafety.isClickable = true
                        classSafety.isClickable = true
                        emailSafety.isClickable = true
                        phoneSafety.isClickable = true
                        bornDateSafety.isClickable = true
                        phoneSafety.isClickable = true
                    }
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
                    setScreen(R.id.item4)
                }
            }
        }
    }

    private fun updateFields() {
        binding.apply {
            mainContainerViewModel.user.let { user ->
                viewModel.apply {
                    user?.photo?.let { mainContainerViewModel.setProfilePhoto(it) }
                }
            }
        }
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
    }
}