package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable

@Serializable
data class Target(
    val number: Int,
    var photo: String?,
    val participants: ArrayList<Participant>
)
