package space.dlsunity.arctour.data.room.data

import androidx.room.Entity
import space.dlsunity.arctour.data.room.data.Doc.Companion.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class Doc(
    var id: String = "",
    var name: String = "",
    var urlsL: String = "",
    var description: String = "",
    var date: String = "",

): Serializable {
    companion object {
        const val TABLE_NAME = "docs_table"
    }
}
