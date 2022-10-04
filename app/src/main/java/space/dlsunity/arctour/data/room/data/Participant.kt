package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    val name: String,
    val lastName: String,
    val SecondName: String?,
    val firstShut: Int,
    val secondShut: Int,
    val score: Int,
)
