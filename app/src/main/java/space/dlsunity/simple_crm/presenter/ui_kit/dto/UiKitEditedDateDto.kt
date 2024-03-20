package space.dlsunity.simple_crm.presenter.ui_kit.dto

import space.dlsunity.simple_crm.back4app.data.Field
import space.dlsunity.simple_crm.data.room.utils.Converters
import space.dlsunity.simple_crm.domain.model.Item
import space.dlsunity.simple_crm.presenter.ui_kit.FieldConstructorHelper.UI_KIT_EDITED_DATE_DTO
import java.util.Date

data class UiKitEditedDateDto(
    var id: String,
    var name: String,
    var date: Date,
    var dateString: String,
    var type: Field.FieldType,
    val itemType: String = UI_KIT_EDITED_DATE_DTO
): Item {
    override fun toJsonString(): String {
        return Converters().fromUiKitEditedDateDto(this)
    }
}
