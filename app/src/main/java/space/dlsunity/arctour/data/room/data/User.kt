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
    @ColumnInfo(name = "photo") var photo: ByteArray?,
    @PrimaryKey var memberId: String,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "last_name") var last_name: String?,
    @ColumnInfo(name = "nick") var nick: String?,
    @ColumnInfo(name = "phone") var phone: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "country") var country: String?,
    @ColumnInfo(name = "city") var city: String?,
): Item {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (photo != null) {
            if (other.photo == null) return false
            if (!photo.contentEquals(other.photo)) return false
        } else if (other.photo != null) return false
        if (memberId != other.memberId) return false
        if (name != other.name) return false
        if (last_name != other.last_name) return false
        if (nick != other.nick) return false
        if (phone != other.phone) return false
        if (email != other.email) return false
        if (country != other.country) return false
        if (city != other.city) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photo?.contentHashCode() ?: 0
        result = 31 * result + memberId.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (last_name?.hashCode() ?: 0)
        result = 31 * result + (nick?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (country?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        return result
    }
}