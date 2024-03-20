package space.dlsunity.simple_crm.presenter.ui_kit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.UiKitCheckBoxFieldBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.BaseItem
import space.dlsunity.simple_crm.presenter.base.adapter.BaseViewHolder
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitCheckBoxFieldDto

class UiKitCheckBoxFieldItem (private val firstAction: (UiKitCheckBoxFieldDto) -> Unit,
                              private val secondAction: (UiKitCheckBoxFieldDto) -> Unit,
                              private val localContext: Context
) : BaseItem<UiKitCheckBoxFieldBinding, UiKitCheckBoxFieldDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitCheckBoxFieldDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitCheckBoxFieldBinding, UiKitCheckBoxFieldDto> {
        val binding = UiKitCheckBoxFieldBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitCheckBoxFieldDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitCheckBoxFieldDto>() {
        override fun areItemsTheSame(oldItem: UiKitCheckBoxFieldDto, newItem: UiKitCheckBoxFieldDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitCheckBoxFieldDto, newItem: UiKitCheckBoxFieldDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitCheckBoxFieldBinding,
        firstAction: (UiKitCheckBoxFieldDto) -> Unit,
        secondAction: (UiKitCheckBoxFieldDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitCheckBoxFieldBinding, UiKitCheckBoxFieldDto>(binding) {

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

        override fun onBind(item: UiKitCheckBoxFieldDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}