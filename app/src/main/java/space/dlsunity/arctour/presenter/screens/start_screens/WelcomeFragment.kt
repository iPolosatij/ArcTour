package space.dlsunity.arctour.presenter.screens.start_screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.User
import space.dlsunity.arctour.data.room.db.AppDatabase
import space.dlsunity.arctour.databinding.WelcomeFragmentBinding
import space.dlsunity.arctour.presenter.base.navigation.NavMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.navigation.navigateSafe

class WelcomeFragment: NavMvvmFragment<AppDestination, WelcomeViewModel>(R.layout.welcome_fragment) {

    private  val args: WelcomeFragmentArgs by navArgs()

    private val logout: Boolean by lazy {
        args.logout
    }

    override val viewModel: WelcomeViewModel by sharedViewModel()

    private val binding: WelcomeFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()
        setUpButtons()
        setUpBackPressed()
    }

    override fun handlerDestination(destination: AppDestination) {
        val action: NavDirections? = when(destination){
            AppDestination.ToMain -> WelcomeFragmentDirections.toMain()
            else -> null
        }
        action?.let { findNavController().navigateSafe(it) }
    }

    private fun observeVm() {
        viewModel.apply {
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)

            message.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { message ->
                    if (message.isEmpty()){
                        toMain()
                    }else{
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
            }

            newUser.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { user ->
                   newUser(user)
                }
            }
        }
    }

    private fun setUpButtons(){
        binding.apply {
            login.setOnClickListener {
                nameValue.visibility = View.GONE
                lastnameValue.visibility = View.GONE
                accept.text = "Login"
                changedOrAccepted(false)
                viewModel.state = WelcomeState.Login
            }
            registration.setOnClickListener {
                changedOrAccepted(false)
                nameValue.visibility = View.VISIBLE
                lastnameValue.visibility = View.VISIBLE
                accept.text = "Registration"
                viewModel.state = WelcomeState.Registration
            }
            accept.setOnClickListener {

                val user: User? = if (
                    loginValue.text?.isNotEmpty() == true
                    && passwordValue.text?.isNotEmpty() == true
                    && viewModel.state == WelcomeState.Login
                ) {
                    viewModel.defaultPhotoProfile?.let { photo ->

                        User(
                            photo = photo,
                            memberId = loginValue.text.toString(),
                            email = loginValue.text.toString(),
                            password = passwordValue.text.toString(),
                        )

                    }
                } else if (
                    loginValue.text?.isNotEmpty() == true
                    && passwordValue.text?.isNotEmpty() == true
                    && nameValue.text?.isNotEmpty() == true
                    && lastnameValue.text?.isNotEmpty() == true
                    && viewModel.state == WelcomeState.Registration
                ) {
                    viewModel.defaultPhotoProfile?.let { photo ->
                        User(
                            photo = photo,
                            memberId = loginValue.text.toString(),
                            password = passwordValue.text.toString(),
                            name = nameValue.text.toString(),
                            last_name = lastnameValue.text.toString(),
                            email = loginValue.text.toString()
                        )
                    }
                } else {
                    null
                }
                if (user == null) {
                    Toast.makeText(
                        requireContext(),
                        " Все поля необходимо заполнить ",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    passwordValue.text?.clear()
                    loginValue.text?.clear()
                    nameValue.text?.clear()
                    lastnameValue.text?.clear()
                    viewModel.acceptUser(user, requireContext())
                }
            }
        }
    }

    private fun newUser(user: User) {
        CoroutineScope(Dispatchers.Default).launch {
            AppDatabase.getInstance(requireContext()).clearAllTables().let {
                binding.apply {
                    accept.isClickable = false
                    passwordValue.isClickable = false
                    loginValue.isClickable = false
                    nameValue.isClickable = false
                    lastnameValue.isClickable = false
                }
                viewModel.loginNewUser(user, requireContext())
            }
        }
    }

    private fun setUpBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(viewModel.state != null){
                    changedOrAccepted(true)
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun changedOrAccepted(changed: Boolean){
        binding.apply {
            if (changed){
                viewModel.state = null
                changedFrame.visibility = View.VISIBLE
                loginFrame.visibility = View.GONE
            }else{
                changedFrame.visibility = View.GONE
                loginFrame.visibility = View.VISIBLE
            }
        }
    }

    private fun handlerError(ex: ErrorModel) {
        when (ex.exception) {

        }
    }
}
enum class WelcomeState{ Login, Registration}