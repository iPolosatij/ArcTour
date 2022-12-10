package space.dlsunity.arctour.presenter.screens.main_container.screens.tournaments.tournament_card.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import space.dlsunity.arctour.R
import space.dlsunity.arctour.data.room.data.Participant
import space.dlsunity.arctour.databinding.ItemParticipantListBinding
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.base.adapter.BaseItem
import space.dlsunity.arctour.presenter.base.adapter.BaseViewHolder

class ParticipantListItem(private val shortTap: (Participant) -> Unit,
                          private val longTap: (Participant) -> Unit,
                          private val target: Int,
) : BaseItem<ItemParticipantListBinding, Participant>
{

    override fun isRelativeItem(item: Item): Boolean = item is Participant

    override fun getLayoutId(): Int = R.layout.item_participant_list

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ItemParticipantListBinding, Participant> {
        val binding = ItemParticipantListBinding.inflate(layoutInflater, parent, false)
        return ItemParticipantListViewHolder(binding, shortTap, longTap, target)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<Participant>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<Participant>() {
        override fun areItemsTheSame(oldItem: Participant, newItem: Participant) =
            oldItem.personalId == newItem.personalId

        override fun areContentsTheSame(oldItem: Participant, newItem: Participant) =
            oldItem == newItem
    }

    private class ItemParticipantListViewHolder(
        binding: ItemParticipantListBinding,
        shortTap: (Participant) -> Unit,
        longTap: (Participant) -> Unit,
        private val target: Int
    ) : BaseViewHolder<ItemParticipantListBinding, Participant>(binding) {

        init {
            binding.apply {
                saveUpBtn.setOnClickListener {
                    if (targetResultFrame.isVisible) {
                        saveUpBtn.setBackgroundResource(R.drawable.ic_down_arrow)
                        targetResultFrame.visibility = View.GONE
                        shortTap(item)
                    } else {
                        saveUpBtn.setBackgroundResource(R.drawable.ic_save_btn)
                        targetResultFrame.visibility = View.VISIBLE
                    }
                }

                scoreM.setOnClickListener {
                    item.targetsResults[target].firstShut = 0
                    setScoreBare()
                }
                score5.setOnClickListener {
                    item.targetsResults[target].firstShut = 5
                    setScoreBare()
                }
                score8.setOnClickListener {
                    item.targetsResults[target].firstShut = 8
                    setScoreBare()
                }
                score10.setOnClickListener {
                    item.targetsResults[target].firstShut = 10
                    setScoreBare()
                }
                score11.setOnClickListener {
                    item.targetsResults[target].firstShut = 11
                    setScoreBare()
                }
                scoreM2.setOnClickListener {
                    item.targetsResults[target].secondShut = 0
                    setScoreBare()
                }
                score52.setOnClickListener {
                    item.targetsResults[target].secondShut = 5
                    setScoreBare()
                }
                score82.setOnClickListener {
                    item.targetsResults[target].secondShut = 8
                    setScoreBare()
                }
                score102.setOnClickListener {
                    item.targetsResults[target].secondShut = 10
                    setScoreBare()
                }
                score112.setOnClickListener {
                    item.targetsResults[target].secondShut = 11
                    setScoreBare()
                }
            }
            setScoreBare()
        }

        fun setScoreBare(){
            clearScoreBare()
            setScore()
        }
        fun clearScoreBare(){
            binding.apply {
                scoreM.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score5.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score8.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score10.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score11.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                scoreM2.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score52.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score82.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score102.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
                score112.setBackgroundResource(R.drawable.gray_frame_12_all_corner_radius)
            }
        }
        fun setScore(){
            binding.apply {
                when(item.targetsResults[target].firstShut){
                    0->{
                        scoreM.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }5->{
                        score5.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }8->{
                        score8.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }10->{
                        score10.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }11->{
                        score11.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }
                }
                when(item.targetsResults[target].secondShut){
                    0->{
                        scoreM2.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }5->{
                        score52.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }8->{
                        score82.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }10->{
                        score102.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }11->{
                        score112.setBackgroundResource(R.drawable.blue_frame_12_all_corner_radius)
                    }
                }
            }
        }

        override fun onBind(item: Participant) {
            super.onBind(item)
            binding.apply {
                participantNameFild.text = "${item.lastName} ${item.name}"
            }
        }
    }
}
