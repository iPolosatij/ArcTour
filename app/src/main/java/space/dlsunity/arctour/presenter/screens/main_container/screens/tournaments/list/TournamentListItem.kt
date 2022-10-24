package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Tournament
import space.dlsunity.arctour.databinding.ItemTournamentsListBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder
import space.dlsunity.arctour.utils.tools.ImageManager

class TournamentListItem (private val shortTap: (Tournament) -> Unit,
                          private val longTap: (Tournament) -> Unit
) : BaseItem<ItemTournamentsListBinding, Tournament>
{

    override fun isRelativeItem(item: Item): Boolean = item is Tournament

    override fun getLayoutId(): Int = R.layout.item_tournaments_list


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ItemTournamentsListBinding, Tournament> {
        val binding = ItemTournamentsListBinding.inflate(layoutInflater, parent, false)
        return ItemContactListViewHolder(binding, shortTap, longTap)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<Tournament>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Tournament>() {
        override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament) =
            oldItem.tournamentId == newItem.tournamentId

        override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament) =
            oldItem == newItem
    }

    private class ItemContactListViewHolder(
        binding: ItemTournamentsListBinding,
        shortTap: (Tournament) -> Unit,
        longTap: (Tournament) -> Unit
    ) : BaseViewHolder<ItemTournamentsListBinding, Tournament>(binding) {

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

        override fun onBind(item: Tournament) {
            super.onBind(item)
            binding.apply {
                CoroutineScope(Dispatchers.IO).launch {
                    if(item.photo.isNotEmpty()) avatar.setImageBitmap(ImageManager.getBitmapsFromUris(listOf(item.photo))[0])
                }
                title.text = item.name
                description.text = item.description
                location.text = item.country + ", " + item.region
                date.text = item.date
            }
        }
    }
}
