package space.dlsunity.arctour.presenter.ui_kit

import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item

object FieldConstructorHelper {

    fun getDto(value: String): Item? {
        if (value.contains(UI_KIT_CHECK_BOX_FIELD_DTO)) {
            return Converters().toCheckBoxFieldDto(value)
        } else if (value.contains(UI_KIT_SWITCH_FIELD_DTO)) {
            return Converters().toUiKitSwitchFieldDto(value)
        } else if (value.contains(UI_KIT_EDITED_DATE_DTO)) {
            return Converters().toUiKitEditedDateDto(value)
        } else if (value.contains(UI_KIT_EDITED_FIELD_DTO)) {
            return Converters().toUiKitEditedFieldDto(value)
        } else if (value.contains(UI_KIT_EDITED_IMAGE_DTO)) {
            return Converters().toUiKitEditedImageDto(value)
        } else if (value.contains(UI_KIT_OBJECT_DTO)) {
            return Converters().toUiKitObjectDto(value)
        } else if (value.contains(UI_KIT_SEARCH_LIST_DTO)) {
            return Converters().toUiKitSearchListDto(value)
        }else if (value.contains(UI_KIT_SELECT_LIST_DTO)) {
            return Converters().toUiKitSelectListDto(value)
        }else{
            return null
        }
    }

    const val UI_KIT_CHECK_BOX_FIELD_DTO = "UI_KIT_CHECK_BOX_FIELD_DTO"
    const val UI_KIT_SWITCH_FIELD_DTO = "UI_KIT_SWITCH_FIELD_DTO"
    const val UI_KIT_EDITED_DATE_DTO = "UI_KIT_EDITED_DATE_DTO"
    const val UI_KIT_EDITED_FIELD_DTO = "UI_KIT_EDITED_FIELD_DTO"
    const val UI_KIT_EDITED_IMAGE_DTO = "UI_KIT_EDITED_IMAGE_DTO"
    const val UI_KIT_OBJECT_DTO = "UI_KIT_OBJECT_DTO"
    const val UI_KIT_SEARCH_LIST_DTO = "UI_KIT_SEARCH_LIST_DTO"
    const val UI_KIT_SELECT_LIST_DTO = "UI_KIT_SELECT_LIST_DTO"
}