package space.dlsunity.arctour.presenter.screens.main_container.screens.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.models.BowClass
import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.databinding.ProfileFragmentBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.MultiItemsAdapter
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.profile.list.BowClassItem
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.extensions.toByteArray

class ProfileFragment : BaseMvvmFragment<ProfileViewModel>(R.layout.profile_fragment) {

    private val mainContainerViewModel: MainContainerViewModel by sharedViewModel()
    override val viewModel: ProfileViewModel by sharedViewModel()
    private val binding: ProfileFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllUsers()
        setUpBinding()
        observeVM()
        observeContainerVM()
        updateFields()
    }

    private val bowClassListAdapter: MultiItemsAdapter by lazy {
        MultiItemsAdapter(
            listOf(
                BowClassItem(::onSelectItem)
            )
        )
    }

    private fun setUpBinding() {
        binding.apply {

            classesList.adapter = bowClassListAdapter
            mainContainerViewModel.photoMain?.let { bitmap ->
                if (bitmap == mainContainerViewModel.defaultPhotoProfile) {
                    profileImage.setImageBitmap(bitmap)
                } else {
                    profileImage.setImageBitmap(bitmap)
                    mainContainerViewModel.setBottomMenuIc(bitmap)
                }
            }

            classValue.setOnClickListener {
                viewModel.selectList.clear()
                mainContainerViewModel.user?.let {
                    val tempList = arrayListOf<BowClass>()
                    for(classBow in requireContext().resources.getStringArray(R.array.class_array)){
                       val bowClassItem =  BowClass(
                            className = classBow,
                            selected = it.bow_class.contains(classBow))
                        tempList.add(bowClassItem)
                        if (bowClassItem.selected)
                            viewModel.selectList.add(bowClassItem)
                    }
                    bowClassListAdapter.submitList(tempList as List<Item>)
                }
                classesChanged.visibility = View.VISIBLE
            }

            addPhoto.setOnClickListener {
                mainContainerViewModel.setScreen(R.id.profile_image)
            }

            saveClasses.setOnClickListener {
                classesChanged.visibility = View.GONE
                val tempList = arrayListOf<String>()
                for(bowClass in viewModel.selectList){
                    tempList.add(bowClass.className)
                }
                viewModel.selectList.clear()

                mainContainerViewModel.user?.bow_class = tempList
                updateFields()
            }

            editSave.setOnClickListener {
                if (viewModel.mode == Mode.Read) {
                    editSave.text = "Save"
                    fieldContainer.background = requireContext().getDrawable(R.color.lite_red)
                    editSave.background =
                        requireContext().getDrawable(R.drawable.white_action_button)
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
                } else {
                    mainContainerViewModel.user?.apply {
                        memberId = emailValue.text.toString()
                        name = nameValue.text.toString()
                        last_name = lastnameValue.text.toString()
                        nick = nickValue.text.toString()
                        phone = phoneValue.text.toString()
                        email = emailValue.text.toString()
                        viewModel.saveUser(this)
                    }
                }
            }
            logOut.setOnClickListener {
                mainContainerViewModel.logOut()
            }
        }
    }

    private fun updateFields(){
        binding.apply {
            mainContainerViewModel.user?.apply {
                var classes = ""
                var first = true
                for (bowClass in bow_class){
                    if (first) {
                        classes = bowClass
                        first = false
                    }
                    else
                        classes = "$classes, $bowClass"
                }
                emailValue.setText(memberId)
                nameValue.setText(name)
                lastnameValue.setText(last_name)
                nickValue.setText(nick)
                phoneValue.setText(phone)
                emailValue.setText(email)
                classValue.text = classes
            }
        }
    }

    private fun observeVM() {
        viewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            needSaveUser.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {user->
                    saveUser(user)
                    updateFields(user)
                }
            }

           userWasLoaded.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {user->
                    updateFields(user)
                }
            }

            userWasSaved.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    binding.apply {
                        editSave.text = "Edit profile"
                        fieldContainer.background = requireContext().getDrawable(R.color.white)
                        editSave.background =
                            requireContext().getDrawable(R.drawable.black_action_button)
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

    private fun updateFields(user: User) {
        binding.apply {
            user.apply {
                mainContainerViewModel.setProfilePhoto(photo)
                nameValue.setText(name, TextView.BufferType.EDITABLE)
                lastnameValue.setText(last_name, TextView.BufferType.EDITABLE)
                nickValue.setText(nick, TextView.BufferType.EDITABLE)
                phoneValue.setText(phone, TextView.BufferType.EDITABLE)
                emailValue.setText(email, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
    }

    private fun onSelectItem(item: BowClass) {
        if (item.selected) {
            viewModel.selectList.remove(item)
        } else {
            viewModel.selectList.add(item)
        }
    }
}