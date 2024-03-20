package space.dlsunity.simple_crm.data.room.data.helpers

data class EditField(
    var id: String = "",
    var name: String = "",
    var value: String = "",
    var status: String = "",
    var type: String = "",
){
    companion object {
        const val DATA_TIME = "data_time"
        const val TEXT_NAME = "text_name"
        const val URL = "data_time"
        const val TEXT_DESCRIPTION = "text_description"
        const val TEXT_TITLE = "text_title"
        const val PICTURE_VAR = "picture_var"
        const val PICTURE_VAL = "picture_val"
        const val TEXT_TEL = "text_tel"
    }
}

