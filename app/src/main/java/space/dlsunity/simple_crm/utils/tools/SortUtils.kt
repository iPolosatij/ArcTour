package space.dlsunity.simple_crm.utils.tools

import space.dlsunity.simple_crm.data.room.data.sketch.Bd_data

object SortUtils {


    fun bdDataIsContain(id: String, chats: List<Bd_data>): Boolean{
        for(chat in chats){
            val chatID = chat.id
            if (chatID == id)
                return true
        }
        return false
    }

    fun getBdDataFromAllData(id: String, allData: List<Bd_data>): Bd_data?{
        for(data in allData){
            val dataID = data.id
            if (dataID == id)
                return data
        }
        return null
    }
}