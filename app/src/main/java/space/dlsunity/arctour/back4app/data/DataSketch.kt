package space.dlsunity.arctour.back4app.data

import androidx.room.Entity
import androidx.room.TypeConverters
import space.dlsunity.arctour.data.room.utils.Converters
import java.io.Serializable

@Entity(tableName = DataSketch.TABLE_NAME, primaryKeys = ["id"])
@TypeConverters(Converters::class)
data class DataSketch(
    val id:String,
    val type: String,
    val fields: List<Field>
): Serializable{

    companion object {
        const val TABLE_NAME = "sketch_table"
    }
}
