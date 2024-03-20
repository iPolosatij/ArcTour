package space.dlsunity.simple_crm.presenter.ui_kit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.UiKitEditedDateBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.BaseItem
import space.dlsunity.simple_crm.presenter.base.adapter.BaseViewHolder
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitEditedDateDto

class UiKitEditedDateItem (private val firstAction: (UiKitEditedDateDto) -> Unit,
                           private val secondAction: (UiKitEditedDateDto) -> Unit,
                           private val localContext: Context
) : BaseItem<UiKitEditedDateBinding, UiKitEditedDateDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitEditedDateDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitEditedDateBinding, UiKitEditedDateDto> {
        val binding = UiKitEditedDateBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitEditedDateDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitEditedDateDto>() {
        override fun areItemsTheSame(oldItem: UiKitEditedDateDto, newItem: UiKitEditedDateDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitEditedDateDto, newItem: UiKitEditedDateDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitEditedDateBinding,
        firstAction: (UiKitEditedDateDto) -> Unit,
        secondAction: (UiKitEditedDateDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitEditedDateBinding, UiKitEditedDateDto>(binding) {

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

        override fun onBind(item: UiKitEditedDateDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}
