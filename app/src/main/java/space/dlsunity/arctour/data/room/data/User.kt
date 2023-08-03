package space.dlsunity.arctour.data.room.data

import androidx.room.Entity
import space.dlsunity.arctour.data.room.data.User.Companion.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class User(
    var id: String = "",
    var name: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var patronymic: String = "",
    var specialtiesL: String = "",
    var skillsL: String = "",
    var locationsL: String = "",
    var avatarUrlL: String = "",
    var emailL: String = "",
    var telL: String = "",
    var docsL: String = "",
    var businessL: String = ""
):Serializable{
    companion object {
        const val TABLE_NAME = "user_table"
    }
}