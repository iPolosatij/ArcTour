package space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.items

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import space.dlsunity.simple_crm.R
import space.dlsunity.simple_crm.databinding.ItemCreateCardPartBinding
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.base.adapter.BaseItem
import space.dlsunity.simple_crm.presenter.base.adapter.BaseViewHolder
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.CheckBoxField
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedDate
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedDatePeriod
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedFieldEmail
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedFieldImage
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedFieldName
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedFieldNumber
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedFieldTelephone
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedFieldText
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.EditedFieldUrl
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.Object
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.ObjectListItem
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.SearchList
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.SelectList
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType.SwitchField
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.dto.ItemCreateWorkCardDto

class ItemCreateWorkCardList  (private val action: (ItemCreateWorkCardDto) -> Unit,
                               private val localContext: Context
) : BaseItem<ItemCreateCardPartBinding, ItemCreateWorkCardDto>
{

    override fun isRelativeItem(item: Item): Boolean = item is ItemCreateWorkCardDto

    override fun getLayoutId(): Int = R.layout.item_create_card_part


    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
    ): BaseViewHolder<ItemCreateCardPartBinding, ItemCreateWorkCardDto> {
        val binding = ItemCreateCardPartBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, action, localContext)
    }

    override fun getDiffUtil(): DiffUtil.ItemCallback<ItemCreateWorkCardDto>  = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ItemCreateWorkCardDto>() {
        override fun areItemsTheSame(oldItem: ItemCreateWorkCardDto, newItem: ItemCreateWorkCardDto) =
            oldItem.workCardItemType == newItem.workCardItemType

        override fun areContentsTheSame(oldItem: ItemCreateWorkCardDto, newItem: ItemCreateWorkCardDto) =
            oldItem == newItem
    }

    private class ItemViewHolder(
        binding: ItemCreateCardPartBinding,
        firstAction: (ItemCreateWorkCardDto) -> Unit,
        val localContext: Context
    ) : BaseViewHolder<ItemCreateCardPartBinding, ItemCreateWorkCardDto>(binding) {

        init {
            binding.clicker.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                firstAction(item)
            }

        }

        override fun onBind(item: ItemCreateWorkCardDto) {
            super.onBind(item)
            binding.apply {
                var view : View? = null
                when(item.workCardItemType){
                    CheckBoxField -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_check_box_field, null)
                    }
                    EditedDate -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_date, null)
                    }
                    EditedDatePeriod -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_date_period, null)
                    }
                    EditedFieldEmail -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_field_email, null)
                    }
                    EditedFieldName -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_field_name, null)
                    }
                    EditedFieldNumber -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_field_number, null)
                    }
                    EditedFieldTelephone -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_field_telephone, null)
                    }
                    EditedFieldText -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_field_text, null)
                    }
                    EditedFieldUrl -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_field_url, null)
                    }
                    EditedFieldImage -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_edited_image, null)
                    }
                    Object -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_object, null)
                    }
                    ObjectListItem -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_object_list_item, null)
                    }
                    SearchList -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_search_list, null)
                    }
                    SelectList -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_select_list, null)
                    }
                    SwitchField -> {
                        view = LayoutInflater.from(localContext).inflate(R.layout.ui_kit_switch_field, null)
                    }
                }
                itemContainer.addView(view)
            }
        }
    }
}