package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable
import space.dlsunity.arctour.domain.model.Item

@Serializable
data class Part(
    val id: String,
    var name:String,
    val targets: List<Target>,
    var complete: Boolean = false
): Item
