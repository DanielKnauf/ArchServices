package knaufdan.android.arch.network

sealed class CallState<ResultType> {
    class Loading<ResultType> : CallState<ResultType>()

    class Success<ResultType>(
        val result: ResultType
    ) : CallState<ResultType>()

    class SuccessEmpty<ResultType> : CallState<ResultType>()

    class Error<ResultType>(
        val code: Int = UNKNOWN_CODE,
        val message: String = ""
    ) : CallState<ResultType>()

    class Failure<ResultType>(
        val throwable: Throwable = UnknownError()
    ) : CallState<ResultType>()

    companion object {
        const val UNKNOWN_CODE = 0
    }
}
