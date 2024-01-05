package space.dlsunity.arctour.presenter.ui_kit.dto

import space.dlsunity.arctour.back4app.data.Field
import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.ui_kit.FieldConstructorHelper.UI_KIT_SELECT_LIST_DTO

data class UiKitSelectListDto(
    val id: String,
    var name: String,
    var list: List<Item>,
    var selectValue: Item,
    var type: Field.FieldType,
    val itemType: String = UI_KIT_SELECT_LIST_DTO
): Item {
    override fun toJsonString(): String {
        return Converters().fromUiKitSelectListDto(this)
    }
}
