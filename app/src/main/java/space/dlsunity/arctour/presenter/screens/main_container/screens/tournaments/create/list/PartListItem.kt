package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.create.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Part
import space.dlsunity.arctour.databinding.ItemPartListBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder

class PartListItem(
    private val shortTap: (Part, ItemPartListBinding) -> Unit,
    private val longTap: (Part) -> Unit,
    private val deleteTap: (Part) -> Unit,
    private val isPossibleDelete: Boolean
) : BaseItem<ItemPartListBinding, Part>
{

    override fun isRelativeItem(item: Item): Boolean = item is Part

    override fun getLayoutId(): Int = R.layout.item_part_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ItemPartListBinding, Part> {
        val binding = ItemPartListBinding.inflate(layoutInflater, parent, false)
        return ItemContactListViewHolder(binding, shortTap, longTap, deleteTap, isPossibleDelete)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<Part>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Part>() {
        override fun areItemsTheSame(oldItem: Part, newItem: Part) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Part, newItem: Part) =
            oldItem == newItem
    }

    private class ItemContactListViewHolder(
        binding: ItemPartListBinding,
        shortTap: (Part, ItemPartListBinding) -> Unit,
        longTap: (Part) -> Unit,
        deleteTap: (Part) -> Unit,
        val isPossibleDelete: Boolean
    ) : BaseViewHolder<ItemPartListBinding, Part>(binding) {

        init {
            binding.partItem.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                shortTap(item, binding)
            }

            if (isPossibleDelete)
                binding.deletePart.setOnClickListener {
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                    deleteTap(item)
                }
            else
                binding.deletePart.visibility = View.INVISIBLE

            binding.partItem.setOnLongClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnLongClickListener true
                longTap(item)
                return@setOnLongClickListener true
            }
        }

        override fun onBind(item: Part) {
            super.onBind(item)
            binding.apply {

                partName.text = item.name

                if(isPossibleDelete)
                    deletePart.visibility = View.VISIBLE
                else
                    deletePart.visibility = View.GONE

                if (item.complete)
                    partItem.background = root.context.getDrawable(R.drawable.blue_frame_12_all_corner_radius)
                else
                    partItem.background = root.context.getDrawable(R.drawable.gray_frame_12_all_corner_radius)
            }
        }
    }
}
