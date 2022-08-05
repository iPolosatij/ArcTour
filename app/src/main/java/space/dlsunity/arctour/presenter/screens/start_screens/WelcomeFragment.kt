package space.dlsunity.arctour.presenter.screens.start_screens

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.WelcomeFragmentBinding
import space.dlsunity.arctour.presenter.base.navigation.NavMvvmFragment
import space.dlsunity.arctour.utils.extensions.collectWhenStarted

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
        if(logout){
            (requireActivity() as space.dlsunity.arctour.MainActivity).mChatService.onDestroy()
            activity?.finish()
        }
    }

    override fun handlerDestination(destination: AppDestination) {

    }

    private fun observeVm() {
        viewModel.apply {
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
        }
    }

    private fun setUpButtons(){
        binding.apply {
            login.setOnClickListener {

            }
            registration.setOnClickListener {

            }
        }
    }
}