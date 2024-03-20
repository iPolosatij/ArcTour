package space.dlsunity.simple_crm.back4app.state

import space.dlsunity.simple_crm.back4app.data.DataClass

sealed class LoadState{
    class Loaded(data: List<DataClass>): LoadState()
    object Processing: LoadState()
    class Error(message: String): LoadState()

}
