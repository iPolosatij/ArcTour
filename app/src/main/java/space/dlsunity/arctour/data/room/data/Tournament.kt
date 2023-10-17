package space.dlsunity.arctour.data.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable
import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item
import java.util.*


@Entity(tableName = "tournament")
@TypeConverters(Converters::class)
@Serializable
data class Tournament(
    @ColumnInfo(name = "photo") var photo: String,
    @PrimaryKey var tournamentId: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "region") var region: String,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "laps") var parts: List<Part>,
    @ColumnInfo(name = "participants") var participants: List<Participant>,
    @ColumnInfo(name = "admins") var admins: List<Participant>,
    @ColumnInfo(name = "teams") var teams: List<TournamentTeam>,
    @ColumnInfo(name = "viewsCounter") var viewsCounter: String = "0",
    @ColumnInfo(name = "emails") var emails: List<String> = arrayListOf(),
    @ColumnInfo(name = "telephones") var telephones: List<String> = arrayListOf(),
    @ColumnInfo(name = "isSelect") var isSelect: Boolean = false,
    @ColumnInfo(name = "uid") var uid: String = UUID.randomUUID().toString(),
): Item