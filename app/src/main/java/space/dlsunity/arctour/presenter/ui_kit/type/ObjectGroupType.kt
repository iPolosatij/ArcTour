package space.dlsunity.arctour.presenter.ui_kit.type

import java.io.Serializable

data class ObjectGroupType(
    val id: String,
    val objectTypesList: List<ObjectType> = listOf()
): Serializable
