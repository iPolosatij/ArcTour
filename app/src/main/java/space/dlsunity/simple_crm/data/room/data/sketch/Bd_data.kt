package space.dlsunity.simple_crm.data.room.data.sketch

import space.dlsunity.simple_crm.domain.model.Item


class Bd_data(
    var id: String,
): Item {
    override fun toJsonString(): String {
        return ""
    }
}