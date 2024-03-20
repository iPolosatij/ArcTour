package space.dlsunity.simple_crm.back4app.data

import androidx.room.Entity
import androidx.room.TypeConverters
import space.dlsunity.simple_crm.data.room.utils.Converters
import java.io.Serializable

@Entity(tableName = DataClass.TABLE_NAME, primaryKeys = ["id"])
@TypeConverters(Converters::class)
data class DataClass(
    val id: String,
    val type: String,
    var fields: List<Field>,
    var state:DataState? = null
): Serializable {
    enum class DataState { Read, Edited }

    companion object {
        const val TABLE_NAME = "data_table"
    }
}