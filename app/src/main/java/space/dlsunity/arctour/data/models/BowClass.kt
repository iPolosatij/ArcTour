package space.dlsunity.arctour.data.models

import space.dlsunity.arctour.domain.model.Item

data class BowClass(
    val className: String,
    val minDist: Int = 0,
    val maxDist: Int = 0,
    val color: String = "",
    var selected: Boolean = false
): Item

