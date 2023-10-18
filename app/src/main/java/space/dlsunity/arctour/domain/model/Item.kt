package space.dlsunity.arctour.domain.model

import java.io.Serializable

interface Item: Serializable {
    fun toJsonString(): String
}