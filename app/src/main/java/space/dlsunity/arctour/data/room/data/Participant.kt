package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable
import space.dlsunity.arctour.domain.model.Item

@Serializable
data class Participant(
    val personalId: String,
    val name: String,
    val lastName: String,
    var secondName: String?,
    var targetsResults: List<TargetResults>,
    var score: Int,
    var bowClass: String?,
    var teamId: String?,
    var isAdmin: Boolean = false
): Item {
    fun getTarget(name: Int):TargetResults?{
        for (target in targetsResults){
            if (target.targetNumber == name)
                return target
        }
        return null
    }
}
