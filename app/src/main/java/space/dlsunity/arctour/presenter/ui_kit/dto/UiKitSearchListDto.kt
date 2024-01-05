package space.dlsunity.arctour.presenter.ui_kit.dto

import space.dlsunity.arctour.back4app.data.Field
import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item
import space.dlsunity.arctour.presenter.ui_kit.FieldConstructorHelper.UI_KIT_SEARCH_LIST_DTO

data class UiKitSearchListDto(
    val id: String,
    var name: String,
    var list: List<Item>,
    var searchResult: List<Item>,
    var type: Field.FieldType,
    val itemType: String = UI_KIT_SEARCH_LIST_DTO
): Item {
    override fun toJsonString(): String {
        return Converters().fromUiKitSearchListDto(this)
    }
}
