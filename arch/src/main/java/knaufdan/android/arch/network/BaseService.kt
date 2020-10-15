package knaufdan.android.arch.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import knaufdan.android.core.util.IGenericType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

abstract class BaseService<Api>(config: ServiceConfig) : IGenericType<Api> {
    private val className: String by lazy { this@BaseService::class.java.simpleName }
    protected val api by lazy { config.createApi() }

    protected fun <ResponseType, LocalType> Call<ResponseType>.execute(
        mapping: ((ResponseType?) -> LocalType)? = null
    ): LiveData<LocalType?> =
        MutableLiveData<LocalType?>().apply {
            bindCall(
                call = this@execute,
                mapping = mapping
            )
        }

    private fun <ResponseType, LocalType> MutableLiveData<LocalType?>.bindCall(
        call: Call<ResponseType>,
        mapping: ((ResponseType?) -> LocalType)?
    ) =
        call.enqueue(
            object : Callback<ResponseType> {
                override fun onFailure(
                    call: Call<ResponseType>,
                    t: Throwable
                ) {
                    postValue(null)
                    Log.w(
                        className,
                        "Call $call failed with msg: ${t.message}"
                    )
                }

                @Suppress("UNCHECKED_CAST")
                override fun onResponse(
                    call: Call<ResponseType>,
                    response: Response<ResponseType>
                ) {
                    val value =
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            mapping?.invoke(responseBody) ?: responseBody as? LocalType
                        } else {
                            null
                        }

                    postValue(value)
                }
            }
        )

    private fun ServiceConfig.createApi(): Api {
        if (baseUrl.isBlank()) {
            throw IllegalArgumentException("BaseUrl for service $className is blank - please set baseUrl via serviceConfig")
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
            .create(getTypeClass())
    }
}
