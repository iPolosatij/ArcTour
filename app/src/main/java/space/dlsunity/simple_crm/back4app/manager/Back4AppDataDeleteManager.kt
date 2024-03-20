package space.dlsunity.simple_crm.back4app.manager

import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import space.dlsunity.simple_crm.back4app.state.DeleteState

class Back4AppDataDeleteManager(
    private val callback: MutableSharedFlow<DeleteState>
) {

    fun deleteObject(id: String, name: String){
        postDeleteState(DeleteState.Processing)
        val query = ParseQuery.getQuery<ParseObject>(name)
        query.get(id).deleteInBackground(){
            if (it != null) {
                if (it.message != null) postDeleteState(DeleteState.Error(it.message!!))
                else postDeleteState(DeleteState.Error(it.stackTraceToString()))
            } else {
                postDeleteState(DeleteState.Deleted)
            }
        }
    }
    private fun postDeleteState(state: DeleteState){
        CoroutineScope(Dispatchers.Main).launch {
            callback.emit(state)
            this.cancel()
        }
    }
}