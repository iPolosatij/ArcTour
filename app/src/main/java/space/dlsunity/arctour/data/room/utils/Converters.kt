package space.dlsunity.arctour.data.room.utils

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import space.dlsunity.arctour.data.room.data.Lap
import space.dlsunity.arctour.data.room.data.Target

class Converters {
    @TypeConverter
    fun fromList(value : List<String>) = Json.encodeToString(value)
    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromListTargets(value : List<Target>) = Json.encodeToString(value)
    @TypeConverter
    fun toListTargets(value: String) = Json.decodeFromString<List<Target>>(value)

    @TypeConverter
    fun fromListLaps(value : List<Lap>) = Json.encodeToString(value)
    @TypeConverter
    fun toListLaps(value: String) = Json.decodeFromString<List<Lap>>(value)

    @TypeConverter
    fun fromBitmap(value : ByteArray) = Json.encodeToString(value)
    @TypeConverter
    fun toBitmap(value: String) = Json.decodeFromString<ByteArray>(value)

}