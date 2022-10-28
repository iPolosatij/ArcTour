package space.dlsunity.arctour.presenter.screens.main_container.screens.profile.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.models.BowClass
import space.dlsunity.arctour.databinding.BowClassItemBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder

class BowClassItem (private val shortTap: (BowClass) -> Unit
) : BaseItem<BowClassItemBinding, BowClass>
{

    override fun isRelativeItem(item: Item): Boolean = item is BowClass

    override fun getLayoutId(): Int = R.layout.item_tournaments_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<BowClassItemBinding, BowClass> {
        val binding = BowClassItemBinding.inflate(layoutInflater, parent, false)
        return ItemContactListViewHolder(binding, shortTap)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<BowClass>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<BowClass>() {
        override fun areItemsTheSame(oldItem: BowClass, newItem: BowClass) =
            oldItem.className == newItem.className

        override fun areContentsTheSame(oldItem: BowClass, newItem: BowClass) =
            oldItem == newItem
    }

    private class ItemContactListViewHolder(
        binding: BowClassItemBinding,
        shortTap: (BowClass) -> Unit
    ) : BaseViewHolder<BowClassItemBinding, BowClass>(binding) {

        init {
            binding.apply {
                clicker.setOnClickListener {
                    if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                    shortTap(item)
                    if(item.selected){
                        item.selected = false
                        checkerIc.setBackgroundResource(R.drawable.ic_check)
                    }else{
                        item.selected = true
                        checkerIc.setBackgroundResource(R.drawable.ic_checked)
                    }
                }
            }
        }

        override fun onBind(item: BowClass) {
            super.onBind(item)
            binding.apply {
                className.text = item.className
                if(item.selected){
                    checkerIc.setBackgroundResource(R.drawable.ic_checked)
                }else{
                    checkerIc.setBackgroundResource(R.drawable.ic_check)
                }
            }
        }
    }
}