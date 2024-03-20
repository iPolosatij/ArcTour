package space.dlsunity.simple_crm.presenter.ui_kit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.UiKitSearchListBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.BaseItem
import space.dlsunity.simple_crm.presenter.base.adapter.BaseViewHolder
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitSearchListDto

class UiKitSearchListItem(private val firstAction: (UiKitSearchListDto) -> Unit,
                          private val secondAction: (UiKitSearchListDto) -> Unit,
                          private val localContext: Context
) : BaseItem<UiKitSearchListBinding, UiKitSearchListDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitSearchListDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitSearchListBinding, UiKitSearchListDto> {
        val binding = UiKitSearchListBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitSearchListDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitSearchListDto>() {
        override fun areItemsTheSame(oldItem: UiKitSearchListDto, newItem: UiKitSearchListDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitSearchListDto, newItem: UiKitSearchListDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitSearchListBinding,
        firstAction: (UiKitSearchListDto) -> Unit,
        secondAction: (UiKitSearchListDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitSearchListBinding, UiKitSearchListDto>(binding) {

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

        override fun onBind(item: UiKitSearchListDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}
