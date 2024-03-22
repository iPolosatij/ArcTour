package space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.dto

import space.dlsunity.simple_crm.data.room.utils.Converters
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.WorkCardItemType

data class ItemCreateWorkCardDto(
    var title: String = "",
    var workCardItemType: WorkCardItemType,
    var subTitle: String = "",
    var description: String = "",
    var hint: String = "",
    var stringList: ArrayList<String> = arrayListOf(),
    var itemList: ArrayList<Item> = arrayListOf()
): Item {
    override fun toJsonString(): String {
        return Converters().fromCreateWorkItem(this)
    }
}