package knaufdan.android.arch.network

sealed class CallState<out ResultType> {
    object Loading : CallState<Nothing>()

    data class Success<ResultType>(
        val result: ResultType
    ) : CallState<ResultType>()

    object SuccessEmpty : CallState<Nothing>()

    data class Error(
        val code: Int = UNKNOWN_CODE,
        val message: String = ""
    ) : CallState<Nothing>()

    data class Failure(
        val throwable: Throwable = UnknownError()
    ) : CallState<Nothing>()

    companion object {
        const val UNKNOWN_CODE = 0
    }
}
