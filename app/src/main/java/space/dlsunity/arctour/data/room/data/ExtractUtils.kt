package space.dlsunity.arctour.data.room.data

object ExtractUtils {

    private const val LIST_SECTION = "|L|"

    fun listStringToString(list: List<String>): String {
        var str = ""
        for (s in list){
            if (str.isEmpty()) {
                str += s
            }else {
                str += LIST_SECTION
                str += s
            }
        }
        return  str
    }

    fun getList(str: String):List<String>{
        return str.split(LIST_SECTION)
    }
}
