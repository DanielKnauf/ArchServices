package knaufdan.android.arch.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import knaufdan.android.arch.base.IGenericType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

abstract class BaseService<Api>(config: ServiceConfig) : IGenericType<Api> {
    protected val api by lazy {
        config.createApi()
    }

    protected fun <ResponseType> Call<ResponseType>.executeCall(): LiveData<ResponseType?> {
        val responseData = MutableLiveData<ResponseType?>()

        this.enqueue(
            object : Callback<ResponseType> {
                override fun onFailure(
                    call: Call<ResponseType>,
                    t: Throwable
                ) {
                    responseData.postValue(null)
                    Log.d(
                        "${this@BaseService::class.simpleName}",
                        "Call $call failed with ${t.message}"
                    )
                }

                override fun onResponse(
                    call: Call<ResponseType>,
                    response: Response<ResponseType>
                ) {
                    if (response.isSuccessful) {
                        responseData.postValue(response.body())
                    } else {
                        responseData.postValue(null)
                    }
                }
            }
        )

        return responseData
    }

    private fun ServiceConfig.createApi(): Api {
        if (baseUrl.isBlank()) {
            throw IllegalArgumentException("BaseUrl for service ${this@BaseService::class.simpleName} is blank - please set nur baseUrl via serviceConfig.")
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
            .create(getTypeClass())
    }
}
