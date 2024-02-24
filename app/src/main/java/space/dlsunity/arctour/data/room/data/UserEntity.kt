package space.dlsunity.arctour.data.room.data

import androidx.room.Entity
import androidx.room.TypeConverters
import space.dlsunity.arctour.back4app.data.User
import space.dlsunity.arctour.data.room.data.UserEntity.Companion.TABLE_NAME
import space.dlsunity.arctour.data.room.utils.Converters
import java.io.Serializable

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
@TypeConverters(Converters::class)
data class UserEntity(
    var id: String = "",
    var type: String = "",
    var pinCode: String = "",
    var nik: String = "",
    var login: String = "",
    var password: String = "",
    var name: String = "",
    var lastname: String = "",
    var patronymic: String = "",
    var birthday: String = "",
    var gender: String = "",
    var country: String = "",
    var index: String = "",
    var settlement: String = "",
    var address: String = "",
    var specialization: List<String> = listOf(),
    var skills: List<String> = listOf(),
    var baseWork: String = "",
    var otherWork: List<String> = listOf(),
    var portfolio: List<String> = listOf(),
    var mail: List<String> = listOf(),
    var tel: List<String> = listOf(),
    var telR: String = "",
    var mailR: String = "",
    var socialNetworking: List<String> = listOf(),
    var experienceDescription: String = "",
    var experienceSince: String = "",
    var education: String = "",
    var studyPlace: String = "",
    var baseQualification: String = "",
    var awards: List<String> = listOf(),
    var business: List<String> = listOf()
):Serializable{

    fun toUser():User{
        return User(
            id,
            type,
            pinCode,
            nik,
            login,
            password,
            name,
            lastname,
            patronymic,
            birthday,
            gender,
            country,
            index,
            settlement,
            address,
            specialization,
            skills,
            baseWork,
            otherWork,
            portfolio,
            mail,
            tel,
            telR,
            mailR,
            socialNetworking,
            experienceDescription,
            experienceSince,
            education,
            studyPlace,
            baseQualification,
            awards,
            business
        )
    }
    companion object {
        const val TABLE_NAME = "user_table"
    }
}