package space.dlsunity.arctour.back4app.state

import space.dlsunity.arctour.back4app.data.DataClass

sealed class LoadState{
    class Loaded(data: List<DataClass>): LoadState()
    object Processing: LoadState()
    class Error(message: String): LoadState()

}
