package space.dlsunity.simple_crm.data.room.data

import androidx.room.Entity
import space.dlsunity.simple_crm.data.room.data.Constructor.Companion.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class Constructor(
    var id: String = "",
    var name: String = "",
    var typeL: String = "",
    var fieldsL: String = "",

): Serializable{
    companion object {
        const val TABLE_NAME = "constructor_table"
    }
}
