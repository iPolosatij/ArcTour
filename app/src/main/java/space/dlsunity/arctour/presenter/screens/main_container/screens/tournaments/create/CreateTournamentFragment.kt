package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Part
import space.dlsunity.arctour.data.room.data.Target
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.databinding.CreateTournamentFragmentBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.MultiItemsAdapter
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerFragment
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create.list.PartListItem
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.tools.DialogHelper
import java.util.*

class CreateTournamentFragment : BaseMvvmFragment<CreateTournamentViewModel>(R.layout.create_tournament_fragment) {

    override val viewModel: CreateTournamentViewModel by sharedViewModel()
    val containerViewModel: MainContainerViewModel by sharedViewModel()

    private val binding: CreateTournamentFragmentBinding by viewBinding()

    private val partListAdapter: MultiItemsAdapter by lazy {
        MultiItemsAdapter(
            listOf(
                PartListItem(::shortTap, ::longTap, ::deleteTap, true)
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
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            showAlert.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let { message ->
                    showAlert(message)
                }
            }

            tournamentWasSaved.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {
                    containerViewModel.setScreen(R.id.item3)
                }
            }
        }
    }

    private fun observeContainerVm() {
        containerViewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            setScreenTitle(MainContainerFragment.CREATE_TOURNAMENT)
            needShowBottomMenu(false)
            showAddBtn(false, "")
        }
    }

    private fun setupBinding() {
        binding.apply {
            viewModel.apply {
                tournamentAddress.addTextChangedListener {
                    if(isPossibleCreate())
                        create.background = requireContext().getDrawable(R.drawable.blue_action_button)
                    else
                        create.background = requireContext().getDrawable(R.drawable.gray_button)
                }
                tournamentCountry.addTextChangedListener {
                    if(isPossibleCreate())
                        create.background = requireContext().getDrawable(R.drawable.blue_action_button)
                    else
                        create.background = requireContext().getDrawable(R.drawable.gray_button)
                }
                tournamentRegion.addTextChangedListener {
                    if(isPossibleCreate())
                        create.background = requireContext().getDrawable(R.drawable.blue_action_button)
                    else
                        create.background = requireContext().getDrawable(R.drawable.gray_button)
                }
                tournamentAddress.addTextChangedListener {
                    if(isPossibleCreate())
                        create.background = requireContext().getDrawable(R.drawable.blue_action_button)
                    else
                        create.background = requireContext().getDrawable(R.drawable.gray_button)
                }
                tournamentName.addTextChangedListener {
                    if(isPossibleCreate())
                        create.background = requireContext().getDrawable(R.drawable.blue_action_button)
                    else
                        create.background = requireContext().getDrawable(R.drawable.gray_button)
                }
                tournamentDescription.addTextChangedListener {
                    if(isPossibleCreate())
                        create.background = requireContext().getDrawable(R.drawable.blue_action_button)
                    else
                        create.background = requireContext().getDrawable(R.drawable.gray_button)
                }

                addPart.setOnClickListener {
                    enterLapName.visibility = View.VISIBLE
                }

                partList.adapter = partListAdapter
                partListAdapter.submitList(viewListParts as List<Item>)

                createPart.setOnClickListener {
                    if (lapName.text.toString().isNotEmpty() && targetCount.text.toString().isNotEmpty()){
                        val targetCount = targetCount.text.toString().toInt()
                        val targets = arrayListOf<Target>()
                        if (targetCount>0){
                            for(i in 1..targetCount)
                            targets.add(Target(i, null))
                        }
                        viewListParts.add(
                            Part(UUID.randomUUID().toString(),
                            name = lapName.text.toString(),
                            targets = targets))
                        enterLapName.visibility = View.GONE
                        partListAdapter.notifyDataSetChanged()
                    }
                }

                create.setOnClickListener {
                    if (isPossibleCreate()) {
                        saveTournament(
                            Tournament(
                                address = tournamentAddress.text.toString(),
                                region = tournamentRegion.text.toString(),
                                country = tournamentCountry.text.toString(),
                                name = tournamentName.text.toString(),
                                description = tournamentDescription.text.toString(),
                                tournamentId = UUID.randomUUID().toString(),
                                date = tournamentDate.text.toString(),
                                parts = viewListParts,
                                participants = viewListParticipant,
                                photo = photo
                            )
                        )
                    }
                }
            }
        }
    }

    private fun isPossibleCreate(): Boolean {
        binding.apply {
            viewModel.apply {
              return (tournamentAddress.text.isNotEmpty()
                      && tournamentCountry.text.isNotEmpty()
                      && tournamentDate.text.isNotEmpty()
                      && tournamentName.text.isNotEmpty()
                      && tournamentRegion.text.isNotEmpty()
                      && tournamentDescription.text.isNotEmpty()
                      && viewListParts.isNotEmpty())
            }
        }
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
                containerViewModel.setScreen(R.id.item3)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    private fun shortTap(item: Part){}
    private fun longTap(item: Part){}
    private fun deleteTap(item: Part){
        viewModel.viewListParts.remove(item)
        partListAdapter.notifyDataSetChanged()
    }

}