package space.dlsunity.arctour.back4app.state

sealed class SavedState{
    object Saved: SavedState()
    object Processing: SavedState()
    class Error(message: String): SavedState()

}
