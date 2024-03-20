package space.dlsunity.simple_crm.presenter.ui_kit.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.UiKitSelectListBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.BaseItem
import space.dlsunity.simple_crm.presenter.base.adapter.BaseViewHolder
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitSelectListDto

class UiKitSelectListItem (private val firstAction: (UiKitSelectListDto) -> Unit,
                           private val secondAction: (UiKitSelectListDto) -> Unit,
                           private val localContext: Context
) : BaseItem<UiKitSelectListBinding, UiKitSelectListDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitSelectListDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitSelectListBinding, UiKitSelectListDto> {
        val binding = UiKitSelectListBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitSelectListDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitSelectListDto>() {
        override fun areItemsTheSame(oldItem: UiKitSelectListDto, newItem: UiKitSelectListDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitSelectListDto, newItem: UiKitSelectListDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitSelectListBinding,
        firstAction: (UiKitSelectListDto) -> Unit,
        secondAction: (UiKitSelectListDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitSelectListBinding, UiKitSelectListDto>(binding) {

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

        override fun onBind(item: UiKitSelectListDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}