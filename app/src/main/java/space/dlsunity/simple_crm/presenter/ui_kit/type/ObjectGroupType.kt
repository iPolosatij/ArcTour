package space.dlsunity.simple_crm.presenter.ui_kit.type

import java.io.Serializable

data class ObjectGroupType(
    val id: String,
    val objectTypesList: List<ObjectType> = listOf()
): Serializable
