package space.dlsunity.arctour.back4app.manager

import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.arctour.back4app.data.DataClass
import space.dlsunity.arctour.back4app.data.Field
import space.dlsunity.arctour.back4app.data.QueryResponse
import space.dlsunity.arctour.back4app.data.User
import space.dlsunity.arctour.back4app.data.UserFields
import space.dlsunity.arctour.back4app.state.LoadState


class Back4AppDataLoadManager(
    private val callback: MutableSharedFlow<LoadState>
    ) {

    fun loadUser(id: String): User{
        val query: ParseQuery<ParseObject> = ParseQuery.getQuery(UserFields.USER)
        query.getInBackground(id).let {task->
           task.result.let {
               return User(
                   id = id,
                   type = UserFields.USER,
                   nik = it.getString(UserFields.NIK)?:"",
                   name = it.getString(UserFields.NAME)?:"",
                   lastname =  it.getString(UserFields.LAST_NAME)?:"",
                   patronymic =  it.getString(UserFields.PATRONYMIC)?:"",
                   birthday =  it.getString(UserFields.BIRTHDAY)?:"",
                   gender =  it.getString(UserFields.GENDER)?:"",
                   country =  it.getString(UserFields.COUNTRY)?:"",
                   index =  it.getString(UserFields.INDEX)?:"",
                   settlement =  it.getString(UserFields.SETTLEMENT)?:"",
                   address =  it.getString(UserFields.ADDRESS)?:"",
                   specialization =  getListFromString(it.getString(UserFields.SPECIALIZATION), UserFields.LIST_DELIVER),
                   skills =  getListFromString(it.getString(UserFields.SKILLS), UserFields.LIST_DELIVER),
                   baseWork =  it.getString(UserFields.BASE_WORK)?:"",
                   otherWork =  getListFromString(it.getString(UserFields.OTHER_WORK), UserFields.LIST_DELIVER),
                   portfolio =  getListFromString(it.getString(UserFields.PORTFOLIO), UserFields.LIST_DELIVER),
                   mail =  getListFromString(it.getString(UserFields.MAIL), UserFields.LIST_DELIVER),
                   tel =  getListFromString(it.getString(UserFields.TEL), UserFields.LIST_DELIVER),
                   socialNetworking =  getListFromString(it.getString(UserFields.SOCIAL_NETWORKING), UserFields.LIST_DELIVER),
                   experienceDescription =  it.getString(UserFields.OTHER_WORK)?:"",
                   experienceSince =  it.getString(UserFields.OTHER_WORK)?:"",
                   education =  it.getString(UserFields.OTHER_WORK)?:"",
                   studyPlace =  it.getString(UserFields.OTHER_WORK)?:"",
                   baseQualification =  it.getString(UserFields.OTHER_WORK)?:"",
                   awards =  getListFromString(it.getString(UserFields.AWARDS), UserFields.LIST_DELIVER),
                   business = getListFromString(it.getString(UserFields.BUSINESS), UserFields.LIST_DELIVER)
               )
           }
        }
    }

    private fun getListFromString(str: String?, deliver:String): List<String> {
        val tempList = arrayListOf<String>()
        str?.split(deliver)?.let { if (it.isNotEmpty()) it.map{s-> tempList.add(s)} }
        return tempList
    }
    fun load( queryResponse: QueryResponse){
        val responseList = arrayListOf<DataClass>()
        val query:ParseQuery<ParseObject>  = ParseQuery.getQuery(queryResponse.dataSketch.type)
        query.whereEqualTo(queryResponse.field, queryResponse.value)
        query.findInBackground { list, e ->
            if (e == null) {
                if (list.isNotEmpty()){
                    for (data in list){
                        val id = data.objectId
                        val type = data.className
                        val dataArray = arrayListOf<Field>()
                        for (field in queryResponse.dataSketch.fields){
                                data.getString(field.name)?.let { dataArray.add(field.copy(value = it) )}
                        }
                        responseList.add(DataClass(id, type, dataArray))
                    }
                }
                postLoadState(LoadState.Loaded(responseList))
            } else {
                if( e.message != null) postLoadState(LoadState.Error(e.message!!))
                else postLoadState(LoadState.Error(e.stackTraceToString()))
            }
        }
    }

    private fun postLoadState(state: LoadState){
        CoroutineScope(Dispatchers.Main).launch {
            callback.emit(state)
            this.cancel()
        }
    }
}