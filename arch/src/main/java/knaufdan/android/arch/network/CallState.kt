package knaufdan.android.arch.network

sealed class CallState<R> {
    class Loading<R> : CallState<R>()

    class Success<R>(
        val result: R
    ) : CallState<R>()

    class SuccessEmpty<R> : CallState<R>()

    class Error<R>(
        val code: Int = UNKNOWN_CODE,
        val message: String = ""
    ) : CallState<R>()

    class Failure<R>(
        val throwable: Throwable = UnknownError()
    ) : CallState<R>()

    companion object {
        const val UNKNOWN_CODE = 0
    }
}
