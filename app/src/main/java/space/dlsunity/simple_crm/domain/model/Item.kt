package space.dlsunity.simple_crm.domain.model

import java.io.Serializable

interface Item: Serializable {
    fun toJsonString(): String
}