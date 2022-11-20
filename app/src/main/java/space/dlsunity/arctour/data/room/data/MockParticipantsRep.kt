package space.dlsunity.arctour.data.room.data

object MockParticipantsRep {
    val nameList = listOf("Сергей","Андрей","Василий","Петр","Александр")
    val nameLastList = listOf("Иванов","Петров","Сидоров","Соколов","Новиков")
    val bowClasses = listOf("Традиция","Лонгбоу","Арбалет","Классический","Инстинктив")
    val secondNames = listOf("Петрович","Иванович","Сергеевич","Викторович","Ибрагимович")

    fun getParticipants(): ArrayList<Participant>{
        val tempList = ArrayList<Participant>()
        for (i in 0..6){
            tempList.add(
                Participant(
                    personalId = "$i",
                    name = nameList.random(),
                    lastName = nameLastList.random(),
                    secondName = secondNames.random(),
                    targetsResults = listOf(),
                    score = 0,
                    bowClass = bowClasses.random(),
                    teamId = null
                )
            )
        }
        return tempList
    }
}