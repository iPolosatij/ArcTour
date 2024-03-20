package space.dlsunity.simple_crm.back4app.state

sealed class DeleteState {
    object Deleted: DeleteState()
    object Processing: DeleteState()
    class Error(message: String): DeleteState()
}