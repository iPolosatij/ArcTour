package space.dlsunity.arctour.back4app.data

import java.util.UUID

data class Field(
    val id:UUID = UUID.randomUUID(),
    var name: String,
    var type: FieldType,
    var value: String
){
    enum class FieldType{
        Other,
        CheckBox,
        EditedDate,
        EditedDatePeriod,
        EditedText,
        EditedNumber,
        EditedTelephone,
        EditedEmail,
        EditedUrl,
        EditedImage,
        SearchList,
        SelectList
    }
}
