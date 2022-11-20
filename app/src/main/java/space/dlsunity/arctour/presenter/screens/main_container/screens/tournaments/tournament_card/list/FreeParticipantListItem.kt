package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Participant
import space.dlsunity.arctour.databinding.FreeParticipantListItemBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder

class FreeParticipantListItem(private val shortTap: (Participant) -> Unit,
                              private val longTap: (Participant) -> Unit,
) : BaseItem<FreeParticipantListItemBinding, Participant>
{

    override fun isRelativeItem(item: Item): Boolean = item is Participant

    override fun getLayoutId(): Int = R.layout.free_participant_list_item

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<FreeParticipantListItemBinding, Participant> {
        val binding = FreeParticipantListItemBinding.inflate(layoutInflater, parent, false)
        return ItemTargetListViewHolder(binding, shortTap, longTap)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<Participant>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Participant>() {
        override fun areItemsTheSame(oldItem: Participant, newItem: Participant) =
            oldItem.personalId == newItem.personalId

        override fun areContentsTheSame(oldItem: Participant, newItem: Participant) =
            oldItem == newItem
    }

    private class ItemTargetListViewHolder(
        binding: FreeParticipantListItemBinding,
        shortTap: (Participant) -> Unit,
        longTap: (Participant) -> Unit
    ) : BaseViewHolder<FreeParticipantListItemBinding, Participant>(binding) {

        init {
            binding.checkItem.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                shortTap(item)
            }

            binding.checkItem.setOnLongClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnLongClickListener true
                longTap(item)
                return@setOnLongClickListener true
            }
        }

        override fun onBind(item: Participant) {
            super.onBind(item)
            binding.apply {
                checkItem.text = "${item.lastName} ${item.name} / ${item.bowClass}"
            }
        }
    }
}
