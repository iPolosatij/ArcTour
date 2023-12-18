package space.dlsunity.arctour.back4app.state

sealed class DeleteState {
    object Deleted: DeleteState()
    object Processing: DeleteState()
    class Error(message: String): DeleteState()
}