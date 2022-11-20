package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable

@Serializable
data class TargetResults(
    val tournamentId: String,
    val targetNumber: Int,
    var firstShut: Int,
    var secondShut: Int,
    val result: Int = firstShut + secondShut,
)
