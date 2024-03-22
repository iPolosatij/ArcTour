package space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.CreateWorkCardFragmentBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.MultiItemsAdapter
import space.dlsunity.simple_crm.presenter.base.mvvm.BaseMvvmFragment
import space.dlsunity.simple_crm.presenter.screens.errors.ErrorModel
import space.dlsunity.simple_crm.presenter.screens.main_container.MainContainerViewModel
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.destinations.CreateWorkDestination
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.dto.ItemCreateWorkCardDto
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.items.ItemCreateWorkCardList
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.viewmodels.CreateWorkCardViewModel
import space.dlsunity.simple_crm.utils.extensions.collectWhenStarted
import space.dlsunity.simple_crm.utils.navigation.navigateSafe

class CreateWorkCardFragment: BaseMvvmFragment<CreateWorkCardViewModel>(R.layout.create_work_card_fragment) {

    private val mainContainerViewModel: MainContainerViewModel by sharedViewModel()
    override val viewModel: CreateWorkCardViewModel by sharedViewModel()
    private val binding: CreateWorkCardFragmentBinding by viewBinding()
    private val listAdapter: MultiItemsAdapter by lazy {
        MultiItemsAdapter(
            listOf(
                ItemCreateWorkCardList(::selectItem, requireContext())
            )
        )
    }

    private fun selectItem(itemCreateWorkCardList: ItemCreateWorkCardDto){
        Toast.makeText(requireContext(), " Выбран тип - ${itemCreateWorkCardList.workCardItemType}", Toast.LENGTH_LONG).show()
        binding.menuFrame.root.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpBinding()
        observeVM()
        observeContainerVM()
    }

    private fun setUpBinding() {
        binding.apply {
            menuFrame.menuList.adapter = listAdapter
            closeDetail.setOnClickListener {
                viewModel.toMain()
            }
            addButton.setOnClickListener {
                binding.menuFrame.root.visibility = View.VISIBLE
                listAdapter.submitList(createItemList())
            }
        }
    }

    private fun closeCardBuilder(){
        binding.apply {

        }
    }

    private fun observeVM() {
        viewModel.apply {
            error.collectWhenStarted(viewLifecycleOwner, ::handlerError)
            destination.collectWhenStarted(viewLifecycleOwner, ::handlerDestination)
        }
    }

    private fun observeContainerVM() {
        mainContainerViewModel.apply {
            setScreenTitle(requireContext().getString(R.string.creator))
            showAddBtn(false, "")
            showFindBtn(false)
            userEntityDownloaded.observe(viewLifecycleOwner) {
                it.getFirstOrNull()?.let {

                }
            }
        }
    }

    private fun handlerDestination(destination:CreateWorkDestination){
        val action: NavDirections =  when(destination){
           is CreateWorkDestination.ToMain -> CreateWorkCardFragmentDirections.toMainFromCreateWorkCard(tab = destination.tab)
        }
        action.let { findNavController().navigateSafe(it) }
    }

    private fun createItemList(): List<Item>{
        val list: ArrayList<Item> = arrayListOf()
        for (type in WorkCardItemType.entries){
            list.add(ItemCreateWorkCardDto(workCardItemType = type))
        }
        return list
    }

    private fun handlerError(ex: ErrorModel) {
        Log.d("unknown", ex.exception.stackTraceToString())
    }
}