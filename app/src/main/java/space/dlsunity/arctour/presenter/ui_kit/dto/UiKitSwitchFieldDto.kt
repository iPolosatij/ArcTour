package space.dlsunity.arctour.presenter.ui_kit.dto

import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.ui_kit.FieldConstructorHelper.UI_KIT_SWITCH_FIELD_DTO

data class UiKitSwitchFieldDto(
    val id: String,
    var name: String,
    var active: Boolean,
    val itemType: String = UI_KIT_SWITCH_FIELD_DTO
): Item {
    override fun toJsonString(): String {
        return  Converters().fromUiKitSwitchFieldDto(this)
    }
}