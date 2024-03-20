package space.dlsunity.simple_crm.data.room.data

import androidx.room.Entity
import space.dlsunity.simple_crm.data.room.data.ConstructorType.Companion.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class ConstructorType(
    var id: String = "",
    var name: String = "",

    ): Serializable {
    companion object {
        const val TABLE_NAME = "constructor_types_table"
    }
}