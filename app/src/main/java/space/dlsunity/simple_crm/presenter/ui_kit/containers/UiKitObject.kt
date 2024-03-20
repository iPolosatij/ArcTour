package space.dlsunity.simple_crm.presenter.ui_kit.containers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.UiKitObjectBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.BaseItem
import space.dlsunity.simple_crm.presenter.base.adapter.BaseViewHolder

class UiKitObject (private val firstAction: (UiKitObjectDto) -> Unit,
                   private val secondAction: (UiKitObjectDto) -> Unit,
                   private val localContext: Context
) : BaseItem<UiKitObjectBinding, UiKitObjectDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is UiKitObjectDto

    override fun getLayoutId(): Int = R.layout.ui_kit_search_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<UiKitObjectBinding, UiKitObjectDto> {
        val binding = UiKitObjectBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, firstAction, secondAction, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<UiKitObjectDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<UiKitObjectDto>() {
        override fun areItemsTheSame(oldItem: UiKitObjectDto, newItem: UiKitObjectDto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UiKitObjectDto, newItem: UiKitObjectDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: UiKitObjectBinding,
        firstAction: (UiKitObjectDto) -> Unit,
        secondAction: (UiKitObjectDto) -> Unit,
        localContext: Context
    ) : BaseViewHolder<UiKitObjectBinding, UiKitObjectDto>(binding) {

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

        override fun onBind(item: UiKitObjectDto) {
            super.onBind(item)
            binding.apply {

            }
        }
    }
}