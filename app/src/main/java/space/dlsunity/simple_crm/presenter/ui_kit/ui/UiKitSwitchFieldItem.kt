package space.dlsunity.simple_crm.presenter.ui_kit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.UiKitSwitchFieldBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.BaseItem
import space.dlsunity.simple_crm.presenter.base.adapter.BaseViewHolder
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitSwitchFieldDto

class UiKitSwitchFieldItem(private val firstAction: (UiKitSwitchFieldDto) -> Unit,
                           private val secondAction: (UiKitSwitchFieldDto) -> Unit,
                           private val localContext: Context
) : BaseItem<UiKitSwitchFieldBinding, UiKitSwitchFieldDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitSwitchFieldDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitSwitchFieldBinding, UiKitSwitchFieldDto> {
        val binding = UiKitSwitchFieldBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitSwitchFieldDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitSwitchFieldDto>() {
        override fun areItemsTheSame(oldItem: UiKitSwitchFieldDto, newItem: UiKitSwitchFieldDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitSwitchFieldDto, newItem: UiKitSwitchFieldDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitSwitchFieldBinding,
        firstAction: (UiKitSwitchFieldDto) -> Unit,
        secondAction: (UiKitSwitchFieldDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitSwitchFieldBinding, UiKitSwitchFieldDto>(binding) {

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                firstAction(item)
            }

            binding.root.setOnLongClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnLongClickListener true
                secondAction(item)
                return@setOnLongClickListener true
            }
        }

        override fun onBind(item: UiKitSwitchFieldDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}