package space.dlsunity.simple_crm.data.room.data.sketch

import androidx.room.Entity
import androidx.room.TypeConverters
import space.dlsunity.simple_crm.data.room.data.sketch.BdEntity.Companion.TABLE_NAME
import space.dlsunity.simple_crm.data.room.utils.Converters

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
@TypeConverters(Converters::class)
class BdEntity(
    var id: String,
) {
    companion object {
        const val TABLE_NAME = "bd_table"
    }
}

fun Bd_data.toEntity(): BdEntity{
    return  BdEntity(
        id = id,
    )
}

fun BdEntity.toData(): Bd_data {
    return  Bd_data(
        id = id,
    )
}