package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable

@Serializable
data class Lap(
    val name:String,
    val targets: List<Target>
)
