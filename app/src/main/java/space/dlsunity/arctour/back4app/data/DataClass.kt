package space.dlsunity.arctour.back4app.data

data class DataClass(
    val id: String,
    val type: String,
    var fields: List<Pair<String, String>>
)