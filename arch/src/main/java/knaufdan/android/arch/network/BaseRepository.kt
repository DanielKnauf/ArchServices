package knaufdan.android.arch.network

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import knaufdan.android.core.common.IGenericType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

abstract class BaseRepository<Api> : IGenericType<Api> {
    private val className: String by lazy {
        this::class.java.simpleName
    }
    protected val api by lazy {
        getRepositoryConfig().createApi()
    }

    abstract fun getRepositoryConfig(): RepositoryConfig

    protected fun <ResponseType, LocalType> Call<ResponseType>.executeAsLiveData(
        mapping: ((ResponseType) -> LocalType) = { result ->
            @Suppress("UNCHECKED_CAST")
            result as LocalType
        }
    ): LiveData<CallState<LocalType>> =
        MutableLiveData<CallState<LocalType>>().apply {
            executeAsCallback(mapping) { callState ->
                postValue(callState)
            }
        }

    @WorkerThread
    protected fun <ResponseType, LocalType> Call<ResponseType>.executeAsCallback(
        mapping: ((ResponseType) -> LocalType) = { result ->
            @Suppress("UNCHECKED_CAST")
            result as LocalType
        },
        onCallStateChanged: (CallState<LocalType>) -> Unit
    ) {
        makeCall(
            mapping = mapping,
            onCallStateChanged = onCallStateChanged
        )
    }

    private fun RepositoryConfig.createApi(): Api {
        if (baseUrl.isBlank()) {
            throw IllegalArgumentException("BaseUrl for service $className is blank - please set baseUrl via serviceConfig")
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
            .create(getTypeClass())
    }

    companion object {
        private fun <ResponseType, LocalType> Call<ResponseType>.makeCall(
            mapping: ((ResponseType) -> LocalType),
            onCallStateChanged: (CallState<LocalType>) -> Unit
        ) {
            onCallStateChanged(CallState.Loading())

            enqueue(
                object : Callback<ResponseType> {
                    override fun onFailure(
                        call: Call<ResponseType>,
                        t: Throwable
                    ) {
                        onCallStateChanged(
                            CallState.Failure(
                                throwable = t
                            )
                        )
                    }

                    @Suppress("UNCHECKED_CAST")
                    override fun onResponse(
                        call: Call<ResponseType>,
                        response: Response<ResponseType>
                    ) {
                        onCallStateChanged(response.toCallState(mapping))
                    }
                }
            )
        }

        private fun <ResponseType, LocalType> Response<ResponseType>.toCallState(
            mapping: ((ResponseType) -> LocalType)
        ): CallState<LocalType> {
            if (isSuccessful) {
                val body = body() ?: return CallState.SuccessEmpty()

                return CallState.Success(mapping(body))
            }

            return CallState.Error(
                code = code(),
                message = message()
            )
        }
    }
}
