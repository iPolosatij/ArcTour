package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Part
import space.dlsunity.arctour.data.room.data.TargetMy
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.databinding.CreateTournamentFragmentBinding
import space.dlsunity.arctour.databinding.ItemPartListBinding
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
        clear()
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
                   checkPossible()
                }
                tournamentCountry.addTextChangedListener {
                   checkPossible()
                }
                tournamentRegion.addTextChangedListener {
                    checkPossible()
                }
                clickerDate.setOnClickListener {
                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    val dpd = DatePickerDialog(requireActivity(), { view, year, monthOfYear, dayOfMonth ->
                        // Display Selected date in textbox
                        tournamentDate.setText("$dayOfMonth ${requireActivity().resources.getStringArray(R.array.month_array)[monthOfYear]}, $year")
                    }, year, month, day)
                    dpd.show()
                }
                tournamentName.addTextChangedListener {
                    checkPossible()
                }
                tournamentDescription.addTextChangedListener {
                   checkPossible()
                }

                addPart.setOnClickListener {
                    enterLapName.visibility = View.VISIBLE
                }

                partList.adapter = partListAdapter
                partListAdapter.submitList(viewListParts as List<Item>)

                createPart.setOnClickListener {
                    if (lapName.text.toString().isNotEmpty() && targetCount.text.toString().isNotEmpty()){
                        val targetsCount = targetCount.text.toString().toInt()
                        val targetsTemp = arrayListOf<TargetMy>()
                        if (targetsCount>0){
                            for(i in 1..targetsCount)
                            targetsTemp.add(TargetMy(i, null))
                        }
                        viewListParts.add(
                            Part(UUID.randomUUID().toString(),
                            name = lapName.text.toString(),
                            targetMIES = targetsTemp))
                        enterLapName.visibility = View.GONE
                        partListAdapter.notifyDataSetChanged()
                        lapName.text?.clear()
                        targetCount.text?.clear()
                        checkPossible()
                        hideKeyboard()
                    }
                }

                create.setOnClickListener {
                    if (isPossibleCreate()) {
                        containerViewModel.user?.let {user->
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
                                    photo = photo,
                                    admins = listOf(user.toParticipant()),
                                    teams = listOf()
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun checkPossible(){
        binding.apply {
            if(isPossibleCreate())
                create.background = requireContext().getDrawable(R.drawable.blue_action_button)
            else
                create.background = requireContext().getDrawable(R.drawable.gray_button)
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

    private fun shortTap(item: Part, binding: ItemPartListBinding){}
    private fun longTap(item: Part){}
    private fun deleteTap(item: Part){
        viewModel.viewListParts.remove(item)
        partListAdapter.notifyDataSetChanged()
    }

    private fun clear(){
        viewModel.apply {
            viewListParticipant.clear()
            viewListParts.clear()
            viewListTargetMy.clear()
        }
    }

}