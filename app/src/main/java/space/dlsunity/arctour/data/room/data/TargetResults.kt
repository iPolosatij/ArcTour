package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable

@Serializable
data class TargetResults(
    val tournamentId: String,
    val targetNumber: Int,
    val firstShut: Int,
    val secondShut: Int,
    val result: Int = firstShut + secondShut,
)
