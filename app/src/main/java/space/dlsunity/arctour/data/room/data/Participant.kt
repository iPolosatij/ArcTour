package space.dlsunity.arctour.data.room.data

import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    val personalId: String,
    val name: String,
    val lastName: String,
    var secondName: String?,
    var targetsResults: List<TargetResults>,
    var score: Int,
    var bowClass: String?,
    var teamId: String?
){
    fun getTarget(name: Int):TargetResults?{
        for (target in targetsResults){
            if (target.targetNumber == name)
                return target
        }
        return null
    }
}
