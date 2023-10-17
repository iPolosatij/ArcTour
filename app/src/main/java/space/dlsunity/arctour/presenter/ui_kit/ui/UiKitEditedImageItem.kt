package space.dlsunity.arctour.presenter.ui_kit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.arctour.R
import space.dlsunity.arctour.databinding.UiKitEditedImageBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder
import space.dlsunity.arctour.presenter.ui_kit.dto.UiKitEditedImageDto

class UiKitEditedImageItem (private val firstAction: (UiKitEditedImageDto) -> Unit,
                            private val secondAction: (UiKitEditedImageDto) -> Unit,
                            private val localContext: Context
) : BaseItem<UiKitEditedImageBinding, UiKitEditedImageDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitEditedImageDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitEditedImageBinding, UiKitEditedImageDto> {
        val binding = UiKitEditedImageBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitEditedImageDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitEditedImageDto>() {
        override fun areItemsTheSame(oldItem: UiKitEditedImageDto, newItem: UiKitEditedImageDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitEditedImageDto, newItem: UiKitEditedImageDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitEditedImageBinding,
        firstAction: (UiKitEditedImageDto) -> Unit,
        secondAction: (UiKitEditedImageDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitEditedImageBinding, UiKitEditedImageDto>(binding) {

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

        override fun onBind(item: UiKitEditedImageDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}