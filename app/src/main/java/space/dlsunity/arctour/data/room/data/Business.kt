package space.dlsunity.arctour.data.room.data

import androidx.room.Entity
import space.dlsunity.arctour.data.room.data.Business.Companion.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class Business(
    var id: String = "",
    var name: String = "",
    var staff: String = "",
    var docsL: String = ""
): Serializable{
    companion object {
        const val TABLE_NAME = "business_table"
    }
}
