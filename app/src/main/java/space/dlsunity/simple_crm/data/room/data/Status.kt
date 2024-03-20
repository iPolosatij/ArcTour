package space.dlsunity.simple_crm.data.room.data

import androidx.room.Entity
import space.dlsunity.simple_crm.data.room.data.Status.Companion.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class Status(
    var id: String = "",
    val name: String = ""
) : Serializable {
    companion object {
        const val TABLE_NAME = "status_table"
    }
}
