package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable
import space.dlsunity.arctour.domain.model.Item

@Serializable
data class TargetMy(
    val number: Int,
    var photo: String?,
    val participants: ArrayList<Participant> = arrayListOf()
): Item
