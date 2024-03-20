package space.dlsunity.simple_crm.data.room.data

import androidx.room.Entity
import space.dlsunity.simple_crm.data.room.data.Section.Companion.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class Section(
    var id: String = "",
    var name: String = "",
    var constructorTypesL: String = ""

    ): Serializable {
    companion object {
        const val TABLE_NAME = "sections_table"
    }
}