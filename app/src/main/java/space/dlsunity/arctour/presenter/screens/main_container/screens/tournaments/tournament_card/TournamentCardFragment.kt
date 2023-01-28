package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Part
import space.dlsunity.arctour.data.room.data.Participant
import space.dlsunity.arctour.data.room.data.TargetMy
import space.dlsunity.arctour.databinding.ItemPartListBinding
import space.dlsunity.arctour.databinding.TournamentFragmentBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.MultiItemsAdapter
import space.dlsunity.arctour.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.arctour.presenter.screens.errors.ErrorModel
import space.dlsunity.arctour.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create.list.PartListItem
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card.list.FreeParticipantListItem
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card.list.ParticipantListItem
import space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card.list.TargetListItem
import space.dlsunity.arctour.utils.extensions.collectWhenStarted
import space.dlsunity.arctour.utils.tools.DialogHelper

class TournamentCardFragment: BaseMvvmFragment<TournamentCardViewModel>(R.layout.tournament_fragment) {

    override val viewModel: TournamentCardViewModel by sharedViewModel()
    val containerViewModel: MainContainerViewModel by sharedViewModel()

    private val binding: TournamentFragmentBinding by viewBinding()

    private val partListAdapter: MultiItemsAdapter by lazy {
        MultiItemsAdapter(
            listOf(
                PartListItem(::shortTap, ::longTap, ::deleteTap, false)
            )
        )
    }

    private val freeParticipantListAdapter: MultiItemsAdapter by lazy {
        MultiItemsAdapter(
            listOf(
                FreeParticipantListItem(::shortTapFreeParticipant, ::longTapFreeParticipant)
            )
        )
    }

    private val targetsAdapter: MultiItemsAdapter by lazy {
        MultiItemsAdapter(
            listOf(
                TargetListItem(::shortTapTarget, ::longTapTarget)
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
            tempFreeParticipantList = containerViewModel.activeTournament?.participants as ArrayList<Participant>
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
            containerViewModel.activeTournament?.name?.let { setScreenTitle(it) }
            needShowBottomMenu(false)
            showAddBtn(false, "")
        }
    }

    private fun setTeam(){
        binding.apply {
            containerViewModel.apply {
                user?.let { user->
                    activeTournament?.let { tour->
                        for (team in tour.teams){
                            for (participant in team.participants){
                                if (participant.personalId == user.memberId) {
                                    viewModel.team = team
                                    userTeam.text = "Команда №${team.teamNumber}"
                                }
                            }
                        }
                        for (admin in tour.admins){
                            if (admin.personalId == user.memberId){
                                arrowBtn.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupBinding() {
        binding.apply {
            lapsList.adapter = partListAdapter
            targetsList.adapter = targetsAdapter
            containerViewModel.apply {
                activeTournament?.apply {
                    descriptionTournament.text = description
                    location.text = "$country, $region, $address"
                    dateTournament.text = date
                    viewModel.viewListParts  = parts as ArrayList<Part>
                    viewModel.tempFreeParticipantList = participants as ArrayList<Participant>
                }
                setTeam()
                freeParticipantList.adapter = freeParticipantListAdapter
                saveTargetBtn.setOnClickListener{
                    activeTournament?.let { tournament ->
                        saveTournament(tournament).let {
                            viewModel.target = null
                            targetFrame.visibility = View.GONE
                            targetName.text = ""
                            targetImage.setBackgroundResource(R.drawable.apple_arrow)
                        }
                    }
                }
                saveTeamBtn.setOnClickListener{
                    activeTournament?.let { tournament ->
                        saveTournament(viewModel.addTeam(tournament))
                    }
                    createTeamFrame.visibility = View.GONE
                    setTeam()
                }
                arrowBtn.setOnClickListener {
                   // viewModel.tempFreeParticipantList = containerViewModel.activeTournament.participants as ArrayList<Participant>
                    freeParticipantListAdapter.submitList(viewModel.tempFreeParticipantList as List<Item>)
                    freeParticipantListAdapter.notifyDataSetChanged()
                    createTeamFrame.visibility = View.VISIBLE
                }
            }
            freeParticipantListAdapter.submitList(viewModel.tempFreeParticipantList as List<Item>)
            partListAdapter.submitList(viewModel.viewListParts as List<Item>)
            targetsAdapter.submitList(viewModel.viewListTargetMy as List<Item>)
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

    private fun shortTap(item: Part, binding: ItemPartListBinding){
        activeLayout(binding)
        targetsAdapter.submitList(item.targetMIES)
        targetsAdapter.notifyDataSetChanged()
    }
    private fun longTap(item: Part){}
    private fun deleteTap(item: Part){}

    private fun longTapTarget(item: TargetMy){}
    private fun shortTapTarget(item: TargetMy){
        viewModel.apply {
            binding.apply {
                if (team != null){
                    target = item
                    val participantListAdapter: MultiItemsAdapter by lazy {
                        MultiItemsAdapter(
                            listOf(
                                ParticipantListItem(::shortTapParticipant, ::longTapParticipant, viewModel.target!!.number-1)
                            )
                        )
                    }
                    participantList.adapter = participantListAdapter
                    item.photo?.let { photo->
                        targetImage.setImageURI(Uri.parse(photo))
                    }
                    team?.let {team->
                        participantListAdapter.submitList(team.participants as List<Item>)
                    }
                    targetFrame.visibility = View.VISIBLE
                }else{
                    Toast.makeText(requireContext(), "You need added to the you team", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun longTapParticipant(item: Participant){}
    private fun shortTapParticipant(item: Participant){}

    private fun longTapFreeParticipant(item: Participant){
    }
    private fun shortTapFreeParticipant(item: Participant){
        viewModel.apply {
            if(tempListParticipant.contains(item)){
                tempListParticipant.remove(item)
                tempFreeParticipantList.add(item)
            }else{
                tempListParticipant.add(item)
                tempFreeParticipantList.remove(item)
            }
        }
    }

    private fun activeLayout(binding: ItemPartListBinding){
        binding.let {newBinding->
            newBinding.partItem.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
            viewModel.activeLayout?.let { activeBinding ->
                activeBinding.partItem.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                viewModel.activeLayout = newBinding
            }
            if (viewModel.activeLayout == null)
                viewModel.activeLayout = newBinding
        }
    }
    private fun clear(){
        viewModel.apply {
            tempListParticipant.clear()
            viewListParts.clear()
            viewListTargetMy.clear()
        }
    }
}

enum class TournamentState{ Read, Edit}