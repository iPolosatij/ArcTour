package space.dlsunity.simple_crm.presenter.ui_kit.containers

import space.dlsunity.simple_crm.data.room.utils.Converters
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.ui_kit.FieldConstructorHelper.UI_KIT_OBJECT_DTO
import space.dlsunity.simple_crm.presenter.ui_kit.type.ObjectType

data class UiKitObjectDto(
    val id: String,
    val itemType: String = UI_KIT_OBJECT_DTO,
    val objectType: ObjectType,
    var title: String? = null,
    val listFields: ArrayList<Item> = arrayListOf()
): Item {
    override fun toJsonString(): String {
        return Converters().fromUiKitObjectDto(this)
    }
}