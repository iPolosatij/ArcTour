package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable

@Serializable
data class TournamentTeam(
    val id: String,
    var participants: List<Participant>,
    val teamName: String,
    val teamNumber: Int,
)