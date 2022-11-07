package space.dlsunity.arctour.data.room.utils

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import space.dlsunity.arctour.data.room.data.Part
import space.dlsunity.arctour.data.room.data.Participant
import space.dlsunity.arctour.data.room.data.TargetMy

class Converters {
    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)
    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromListTargets(value : List<TargetMy>) = Json.encodeToString(value)
    @TypeConverter
    fun toListTargets(value: String) = Json.decodeFromString<List<TargetMy>>(value)

    @TypeConverter
    fun fromListLaps(value : List<Part>) = Json.encodeToString(value)
    @TypeConverter
    fun toListLaps(value: String) = Json.decodeFromString<List<Part>>(value)

    @TypeConverter
    fun fromListParticipants(value : List<Participant>) = Json.encodeToString(value)
    @TypeConverter
    fun toListParticipants(value: String) = Json.decodeFromString<List<Participant>>(value)

    @TypeConverter
    fun fromBitmap(value : ByteArray) = Json.encodeToString(value)
    @TypeConverter
    fun toBitmap(value: String) = Json.decodeFromString<ByteArray>(value)

}