package space.dlsunity.arctour.presenter.ui_kit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.UiKitEditedFieldBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder
import space.dlsunity.arctour.presenter.ui_kit.dto.UiKitEditedFieldDto

class UiKitEditedFieldItem (private val firstAction: (UiKitEditedFieldDto) -> Unit,
                            private val secondAction: (UiKitEditedFieldDto) -> Unit,
                            private val localContext: Context
) : BaseItem<UiKitEditedFieldBinding, UiKitEditedFieldDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitEditedFieldDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitEditedFieldBinding, UiKitEditedFieldDto> {
        val binding = UiKitEditedFieldBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitEditedFieldDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitEditedFieldDto>() {
        override fun areItemsTheSame(oldItem: UiKitEditedFieldDto, newItem: UiKitEditedFieldDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitEditedFieldDto, newItem: UiKitEditedFieldDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitEditedFieldBinding,
        firstAction: (UiKitEditedFieldDto) -> Unit,
        secondAction: (UiKitEditedFieldDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitEditedFieldBinding, UiKitEditedFieldDto>(binding) {

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

        override fun onBind(item: UiKitEditedFieldDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}