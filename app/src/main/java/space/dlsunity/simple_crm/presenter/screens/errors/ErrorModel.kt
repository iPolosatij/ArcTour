package space.dlsunity.simple_crm.presenter.screens.errors

class ErrorModel(
    val exception: Throwable,
    val retryAction: () -> Unit,
)

sealed class NavigateException : Exception() {

    abstract val traceId: String

    data class ItemNotExist(
        override val traceId: String = "Error: item not exist"
    ) : NavigateException()
}
