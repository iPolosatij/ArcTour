package space.dlsunity.arctour.data.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.Serializable
import space.dlsunity.arctour.data.room.utils.Converters
import space.dlsunity.arctour.domain.model.Item

@Entity(tableName = "user")
@TypeConverters(Converters::class)
@Serializable
data class User(
    @ColumnInfo(name = "photo") var photo: ByteArray,
    @ColumnInfo(name = "photo_url") var photoUrl: String = "",
    @PrimaryKey var memberId: String = "",
    @ColumnInfo(name = "password") var password: String = "",
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "last_name") var last_name: String = "",
    @ColumnInfo(name = "nick") var nick: String = "",
    @ColumnInfo(name = "phone") var phone: String = "",
    @ColumnInfo(name = "email") var email: String = "",
    @ColumnInfo(name = "bow_class") var bow_class: List<String> = listOf(),
    @ColumnInfo(name = "country") var country: String = "",
    @ColumnInfo(name = "city") var city: String = "",
    @ColumnInfo(name = "second_name") var second_name: String = "",
): Item {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (!photo.contentEquals(other.photo)) return false
        if (photoUrl != other.photoUrl) return false
        if (memberId != other.memberId) return false
        if (name != other.name) return false
        if (last_name != other.last_name) return false
        if (nick != other.nick) return false
        if (phone != other.phone) return false
        if (email != other.email) return false
        if (bow_class != other.bow_class) return false
        if (country != other.country) return false
        if (city != other.city) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photo.contentHashCode()
        result = 31 * result + photoUrl.hashCode()
        result = 31 * result + memberId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + last_name.hashCode()
        result = 31 * result + nick.hashCode()
        result = 31 * result + phone.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + bow_class.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + city.hashCode()
        return result
    }

    fun toParticipant(): Participant{
        return Participant(
            personalId = this.memberId,
            name = this.name,
            lastName = this.last_name,
            secondName = this.second_name,
            targetsResults = listOf(),
            score = 0,
            bowClass = null,
            teamId = null
        )
    }
}