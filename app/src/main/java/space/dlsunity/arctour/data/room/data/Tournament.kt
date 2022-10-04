package space.dlsunity.arctour.data.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable
import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item


@Entity(tableName = "tournament")
@TypeConverters(Converters::class)
@Serializable
data class Tournament(
    @ColumnInfo(name = "photo") var photo: String?,
    @PrimaryKey var tournamentId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "region") var region: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "laps") var laps: List<Lap>,
    @ColumnInfo(name = "participants") var participants: List<Participant>,
): Item