package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.TargetMy
import space.dlsunity.arctour.databinding.ItemTargetsListBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder

class TargetListItem(private val shortTap: (TargetMy) -> Unit,
                     private val longTap: (TargetMy) -> Unit,
) : BaseItem<ItemTargetsListBinding, TargetMy>
{

    override fun isRelativeItem(item: Item): Boolean = item is TargetMy

    override fun getLayoutId(): Int = R.layout.item_targets_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ItemTargetsListBinding, TargetMy> {
        val binding = ItemTargetsListBinding.inflate(layoutInflater, parent, false)
        return ItemTargetListViewHolder(binding, shortTap, longTap)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<TargetMy>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<TargetMy>() {
        override fun areItemsTheSame(oldItem: TargetMy, newItem: TargetMy) =
            oldItem.number == newItem.number

        override fun areContentsTheSame(oldItem: TargetMy, newItem: TargetMy) =
            oldItem == newItem
    }

    private class ItemTargetListViewHolder(
        binding: ItemTargetsListBinding,
        shortTap: (TargetMy) -> Unit,
        longTap: (TargetMy) -> Unit
    ) : BaseViewHolder<ItemTargetsListBinding, TargetMy>(binding) {

        init {
            binding.clicker.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                shortTap(item)
            }

            binding.clicker.setOnLongClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnLongClickListener true
                longTap(item)
                return@setOnLongClickListener true
            }
        }

        override fun onBind(item: TargetMy) {
            super.onBind(item)
            binding.apply {
                titleTarget.text = "Мишень № ${item.number}"
            }
        }
    }
}
