package space.dlsunity.arctour.back4app.manager

import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.arctour.back4app.data.User
import space.dlsunity.arctour.back4app.data.UserFields
import space.dlsunity.arctour.back4app.state.SavedState

class Back4AppDataSavedManager(private val callback: MutableSharedFlow<SavedState>) {
    fun save(
        className: String,
        valueMap: HashMap<String, String>
    ) {
        postSavedState(SavedState.Processing)
        val savedObject = ParseObject(className)
        for (value in valueMap) {
            savedObject.put(value.key, value.value)
        }
        savedObject.saveInBackground {
            if (it != null) {
                if( it.message != null) postSavedState(SavedState.Error(it.message!!))
                else postSavedState(SavedState.Error(it.stackTraceToString()))
            } else {
                postSavedState(SavedState.Saved)
            }
        }
    }

    fun replace(
        id: String,
        className: String,
        valueMap: HashMap<String, String>
    ) {
        postSavedState(SavedState.Processing)
        CoroutineScope(Dispatchers.Main).launch {
            val query = ParseQuery.getQuery<ParseObject>(className)
            query.get(id).let { savedObject ->
                for (value in valueMap) {
                    savedObject.put(value.key, value.value)
                }
                savedObject.saveInBackground {
                    if (it != null) {
                        if (it.message != null) postSavedState(SavedState.Error(it.message!!))
                        else postSavedState(SavedState.Error(it.stackTraceToString()))
                    } else {
                        postSavedState(SavedState.Saved)
                    }
                }
            }
        }
    }

    fun loadUser(user:User){
        postSavedState(SavedState.Processing)
        CoroutineScope(Dispatchers.Main).launch {
            val savedObject = ParseObject(UserFields.USER)
            savedObject.put(UserFields.NIK, user.nik)
            savedObject.put(UserFields.NAME, user.name)
            savedObject.put(UserFields.LAST_NAME, user.lastname)
            savedObject.put(UserFields.PATRONYMIC, user.patronymic)
            savedObject.put(UserFields.BIRTHDAY, user.birthday)
            savedObject.put(UserFields.GENDER, user.gender)
            savedObject.put(UserFields.COUNTRY, user.country)
            savedObject.put(UserFields.INDEX, user.index)
            savedObject.put(UserFields.SETTLEMENT, user.settlement)
            savedObject.put(UserFields.ADDRESS, user.address)
            savedObject.put(UserFields.SPECIALIZATION, getStringFromList(user.specialization))
            savedObject.put(UserFields.SKILLS, getStringFromList(user.skills))
            savedObject.put(UserFields.BASE_WORK, user.baseWork)
            savedObject.put(UserFields.OTHER_WORK, getStringFromList(user.otherWork))
            savedObject.put(UserFields.PORTFOLIO, getStringFromList(user.portfolio))
            savedObject.put(UserFields.MAIL, getStringFromList(user.mail))
            savedObject.put(UserFields.TEL, getStringFromList(user.tel))
            savedObject.put(UserFields.SOCIAL_NETWORKING, getStringFromList(user.socialNetworking))
            savedObject.put(UserFields.EXPERIENCE_DESCRIPTION, user.experienceDescription)
            savedObject.put(UserFields.EXPERIENCE_SINCE, user.experienceSince)
            savedObject.put(UserFields.EDUCATION, user.education)
            savedObject.put(UserFields.STUDY_PLACE, user.studyPlace)
            savedObject.put(UserFields.BASE_QUALIFICATION, user.baseQualification)
            savedObject.put(UserFields.EXPERIENCE_SINCE, user.experienceSince)
            savedObject.put(UserFields.AWARDS, getStringFromList(user.awards))
            savedObject.put(UserFields.BUSINESS, getStringFromList(user.business))
            savedObject.saveInBackground {
                if (it != null) {
                    if (it.message != null) postSavedState(SavedState.Error(it.message!!))
                    else postSavedState(SavedState.Error(it.stackTraceToString()))
                } else {
                    postSavedState(SavedState.Saved)
                }
            }
        }
    }

    fun replaceUser(id: String, user:User){
        postSavedState(SavedState.Processing)
        CoroutineScope(Dispatchers.Main).launch {
            val query = ParseQuery.getQuery<ParseObject>(UserFields.USER)
            query.get(id).let { savedObject ->
                savedObject.put(UserFields.NIK, user.nik)
                savedObject.put(UserFields.NAME, user.name)
                savedObject.put(UserFields.LAST_NAME, user.lastname)
                savedObject.put(UserFields.PATRONYMIC, user.patronymic)
                savedObject.put(UserFields.BIRTHDAY, user.birthday)
                savedObject.put(UserFields.GENDER, user.gender)
                savedObject.put(UserFields.COUNTRY, user.country)
                savedObject.put(UserFields.INDEX, user.index)
                savedObject.put(UserFields.SETTLEMENT, user.settlement)
                savedObject.put(UserFields.ADDRESS, user.address)
                savedObject.put(UserFields.SPECIALIZATION, getStringFromList(user.specialization))
                savedObject.put(UserFields.SKILLS, getStringFromList(user.skills))
                savedObject.put(UserFields.BASE_WORK, user.baseWork)
                savedObject.put(UserFields.OTHER_WORK, getStringFromList(user.otherWork))
                savedObject.put(UserFields.PORTFOLIO, getStringFromList(user.portfolio))
                savedObject.put(UserFields.MAIL, getStringFromList(user.mail))
                savedObject.put(UserFields.TEL, getStringFromList(user.tel))
                savedObject.put(
                    UserFields.SOCIAL_NETWORKING,
                    getStringFromList(user.socialNetworking)
                )
                savedObject.put(UserFields.EXPERIENCE_DESCRIPTION, user.experienceDescription)
                savedObject.put(UserFields.EXPERIENCE_SINCE, user.experienceSince)
                savedObject.put(UserFields.EDUCATION, user.education)
                savedObject.put(UserFields.STUDY_PLACE, user.studyPlace)
                savedObject.put(UserFields.BASE_QUALIFICATION, user.baseQualification)
                savedObject.put(UserFields.EXPERIENCE_SINCE, user.experienceSince)
                savedObject.put(UserFields.AWARDS, getStringFromList(user.awards))
                savedObject.put(UserFields.BUSINESS, getStringFromList(user.business))
                savedObject.saveInBackground {
                    if (it != null) {
                        if (it.message != null) postSavedState(SavedState.Error(it.message!!))
                        else postSavedState(SavedState.Error(it.stackTraceToString()))
                    } else {
                        postSavedState(SavedState.Saved)
                    }
                }
            }
        }
    }



    private fun getStringFromList(list:List<String>): String{
        var str = ""
        if (list.isNotEmpty()){
            list.map { s-> str = if(str.isEmpty()) str + UserFields.LIST_DELIVER + s else str + s }
        }
        return str
    }

    private fun postSavedState(state: SavedState){
        CoroutineScope(Dispatchers.Main).launch {
            callback.emit(state)
            this.cancel()
        }
    }
}