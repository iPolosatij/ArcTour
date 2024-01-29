package space.dlsunity.arctour.presenter.screens.start_screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.back4app.state.AuthorisationState
import space.dlsunity.arctour.back4app.state.AuthorisationState.SignUpSuccess
import space.dlsunity.arctour.databinding.WelcomeFragmentBinding
import space.dlsunity.arctour.presenter.base.navigation.NavMvvmFragment
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.navigation.navigateSafe

class WelcomeFragment :
    NavMvvmFragment<AppDestination, WelcomeViewModel>(R.layout.welcome_fragment) {

    private val args: WelcomeFragmentArgs by navArgs()

    private val logout: Boolean by lazy {
        args.logout
    }

    override val viewModel: WelcomeViewModel by sharedViewModel()

    private val binding: WelcomeFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()
        setUpButtons()
        viewModel.getUser()
    }

    override fun handlerDestination(destination: AppDestination) {
        val action: NavDirections? = when (destination) {
            AppDestination.ToMain -> WelcomeFragmentDirections.toMain()
            else -> null
        }
        action?.let { findNavController().navigateSafe(it) }
    }

    private fun handlerAuthorisation(authorisationState: AuthorisationState) {
        when (authorisationState) {
            is SignUpSuccess -> {
                viewModel.user = authorisationState.user
                showSetPin()
            }

            is AuthorisationState.Success -> {
                showMessage(authorisationState.sessionToken)
                viewModel.toMain(authorisationState.sessionToken)
                showLoader(false)
            }

            is AuthorisationState.AuthError -> {
                authorisationState.message?.let { showMessage(it) }
                showLoader(false)
            }

            is AuthorisationState.Processing -> showLoader(true)
            is AuthorisationState.SuccessPinCode -> {
                viewModel.user?.let {
                    viewModel.signIn(it.login, it.password)
                    showLoader(false)
                }
            }
            is AuthorisationState.ShowPinCode -> {
                    showEnterPin()
            }
        }
    }

    private fun observeVm() {
        viewModel.apply {
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
            authorisationCallBack.collectWhenStarted(viewLifecycleOwner, ::handlerAuthorisation)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showLoader(needShoe: Boolean) {
        if (needShoe) {
            binding.loginFrame.visibility = View.GONE
            binding.loader.visibility = View.VISIBLE
        } else {
            binding.loginFrame.visibility = View.VISIBLE
            binding.loader.visibility = View.GONE
        }
    }

    private fun showSetPin() {
        binding.apply {
            pinFrame.visibility = View.VISIBLE
            setPinBtn.setOnClickListener {
                val pin = pinCode.text.toString()
                if (pin.length < 4){
                    Toast.makeText(requireContext(), "Введите ПИН код", Toast.LENGTH_LONG).show()
                }else{
                    viewModel.setPin(pin)
                }
            }
        }
    }

    private fun showEnterPin() {
        binding.apply {
            pinFrame.visibility = View.VISIBLE
            pinTitle.text = "Введите ПИН"
            setPinBtn.setOnClickListener {
                val pin = pinCode.text.toString()
                if (pin.length < 4){
                    Toast.makeText(requireContext(), "Введите ПИН код", Toast.LENGTH_LONG).show()
                }else{
                    viewModel.enterPin(pin)
                }
            }
        }
    }

    private fun setUpButtons() {
        binding.apply {
            login.setOnClickListener {
                if (loginText.isVisible && !emailText.isVisible) {
                    if (loginText.text.isNotEmpty() && passwordText.text.isNotEmpty())
                        viewModel.signIn(loginText.text.toString(), passwordText.text.toString())
                    else
                        showMessage("Enter login or password")
                } else {
                    loginText.visibility = View.VISIBLE
                    passwordText.visibility = View.VISIBLE
                    emailText.visibility = View.GONE
                }
            }
            registration.setOnClickListener {
                if (loginText.isVisible && emailText.isVisible) {
                    if (loginText.text.isNotEmpty() && passwordText.text.isNotEmpty() && emailText.text.isNotEmpty())
                        viewModel.signUp(
                            loginText.text.toString(),
                            passwordText.text.toString(),
                            emailText.text.toString()
                        )
                    else
                        showMessage("Enter login , password or email")
                } else {
                    loginText.visibility = View.VISIBLE
                    passwordText.visibility = View.VISIBLE
                    emailText.visibility = View.VISIBLE
                }
            }
        }
    }
}