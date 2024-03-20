package space.dlsunity.arctour.presenter.screens.start_screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.StartFragmentBinding
import space.dlsunity.arctour.presenter.base.navigation.NavMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.navigation.navigateSafe


class StartFragment: NavMvvmFragment<AppDestination, StartViewModel>(R.layout.start_fragment) {

    override val viewModel: StartViewModel by sharedViewModel()

    private val binding: StartFragmentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVm()
    }



    private fun observeVm() {
        viewModel.apply {
            startChange()
            changer.collectWhenStarted(viewLifecycleOwner, ::changeLogo)
            navigateCommander.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeLogo(change: Int){
        when (change){
            1 -> {
                binding.imageView.setImageDrawable(requireContext().getDrawable(R.drawable.white_logo))
            }
            2 -> {
                binding.imageView.setImageDrawable(requireContext().getDrawable(R.drawable.blue_logo))
            }
            3 -> {
                binding.imageView.setImageDrawable(requireContext().getDrawable(R.drawable.green_logo))
            }
            4 -> {
                binding.imageView.setImageDrawable(requireContext().getDrawable(R.drawable.red_logo))
            }
        }

    }

    override fun handlerDestination(destination: AppDestination) {
        viewModel.active = false
        val action: NavDirections? = when(destination){
            AppDestination.ToWelcome -> StartFragmentDirections.toWelcomeFrag()
            AppDestination.ToMain -> StartFragmentDirections.toMain()
            else -> null
        }
        action?.let { findNavController().navigateSafe(it) }
    }

    private fun handlerError(ex: ErrorModel) {
        when (ex.exception) {

        }
    }
}