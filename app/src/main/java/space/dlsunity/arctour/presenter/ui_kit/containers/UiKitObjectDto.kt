package space.dlsunity.arctour.presenter.ui_kit.containers

import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.ui_kit.FieldConstructorHelper.UI_KIT_OBJECT_DTO
import space.dlsunity.arctour.presenter.ui_kit.type.ObjectType

data class UiKitObjectDto(
    val id: String,
    val itemType: String = UI_KIT_OBJECT_DTO,
    val objectType: ObjectType,
    var title: String? = null,
    val listFields: List<Item> = listOf()
): Item {
    override fun toJsonString(): String {
        return Converters().fromUiKitObjectDto(this)
    }
}