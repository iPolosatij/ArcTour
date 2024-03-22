package space.dlsunity.simple_crm.data.room.utils

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import space.dlsunity.simple_crm.back4app.data.Field
import space.dlsunity.simple_crm.presenter.screens.main_container.screens.works.screens.dto.ItemCreateWorkCardDto
import space.dlsunity.simple_crm.presenter.ui_kit.containers.UiKitObjectDto
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitCheckBoxFieldDto
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitEditedDateDto
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitEditedFieldDto
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitEditedImageDto
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitSearchListDto
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitSelectListDto
import space.dlsunity.simple_crm.presenter.ui_kit.dto.UiKitSwitchFieldDto
import space.dlsunity.simple_crm.presenter.ui_kit.type.ObjectGroupType
import space.dlsunity.simple_crm.presenter.ui_kit.type.ObjectType

class Converters {
    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)
    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromBitmap(value : ByteArray) = Json.encodeToString(value)
    @TypeConverter
    fun toBitmap(value: String) = Json.decodeFromString<ByteArray>(value)

    @TypeConverter
    fun fromField(value : Field) = Json.encodeToString(value)
    @TypeConverter
    fun toField(value: String) = Json.decodeFromString<Field>(value)

    @TypeConverter
    fun fromListField(value : List<Field>) = Json.encodeToString(value)
    @TypeConverter
    fun toListField(value: String) = Json.decodeFromString<List<Field>>(value)

    @TypeConverter
    fun fromCheckBoxFieldDto(value : UiKitCheckBoxFieldDto) = Json.encodeToString(value)
    @TypeConverter
    fun toCheckBoxFieldDto(value: String) = Json.decodeFromString<UiKitCheckBoxFieldDto>(value)

    @TypeConverter
    fun fromUiKitEditedDateDto(value : UiKitEditedDateDto) = Json.encodeToString(value)
    @TypeConverter
    fun toUiKitEditedDateDto(value: String) = Json.decodeFromString<UiKitEditedDateDto>(value)

    @TypeConverter
    fun fromUiKitEditedFieldDto(value : UiKitEditedFieldDto) = Json.encodeToString(value)
    @TypeConverter
    fun toUiKitEditedFieldDto(value: String) = Json.decodeFromString<UiKitEditedFieldDto>(value

    )@TypeConverter
    fun fromUiKitEditedImageDto(value : UiKitEditedImageDto) = Json.encodeToString(value)
    @TypeConverter
    fun toUiKitEditedImageDto(value: String) = Json.decodeFromString<UiKitEditedImageDto>(value)

    @TypeConverter
    fun fromUiKitSearchListDto(value : UiKitSearchListDto) = Json.encodeToString(value)
    @TypeConverter
    fun toUiKitSearchListDto(value: String) = Json.decodeFromString<UiKitSearchListDto>(value)

    @TypeConverter
    fun fromUiKitSelectListDto(value : UiKitSelectListDto) = Json.encodeToString(value)
    @TypeConverter
    fun toUiKitSelectListDto(value: String) = Json.decodeFromString<UiKitSelectListDto>(value)

    @TypeConverter
    fun fromUiKitSwitchFieldDto(value : UiKitSwitchFieldDto) = Json.encodeToString(value)
    @TypeConverter
    fun toUiKitSwitchFieldDto(value: String) = Json.decodeFromString<UiKitSwitchFieldDto>(value)

    @TypeConverter
    fun fromUiKitObjectDto(value : UiKitObjectDto) = Json.encodeToString(value)
    @TypeConverter
    fun toUiKitObjectDto(value: String) = Json.decodeFromString<UiKitObjectDto>(value)

    @TypeConverter
    fun fromCreateWorkItem(value : ItemCreateWorkCardDto) = Json.encodeToString(value)
    @TypeConverter
    fun toCreateWorkItem(value: String) = Json.decodeFromString<ItemCreateWorkCardDto>(value)

    @TypeConverter
    fun fromObjectGroupType(value : ObjectGroupType) = Json.encodeToString(value)
    @TypeConverter
    fun toObjectGroupType(value: String) = Json.decodeFromString<ObjectGroupType>(value)

    @TypeConverter
    fun fromObjectType(value : ObjectType) = Json.encodeToString(value)
    @TypeConverter
    fun toObjectType(value: String) = Json.decodeFromString<ObjectType>(value)



}