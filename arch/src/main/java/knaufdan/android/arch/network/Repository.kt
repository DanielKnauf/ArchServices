package knaufdan.android.arch.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import knaufdan.android.arch.base.IGenericType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class Repository<ServiceType>(baseUrl: String) : IGenericType<ServiceType> {
    protected val api by lazy {
        createApi(
            baseUrl,
            getTypeClass()
        )
    }

    protected fun <ResponseType> Call<ResponseType>.executeCall(): LiveData<ResponseType?> {
        val responseData = MutableLiveData<ResponseType?>()

        this.enqueue(object : Callback<ResponseType> {
            override fun onFailure(
                call: Call<ResponseType>,
                t: Throwable
            ) {
                responseData.postValue(null)
                Log.d("${this@Repository::class.simpleName}", "Call $call failed with ${t.message}")
            }

            override fun onResponse(
                call: Call<ResponseType>,
                response: Response<ResponseType>
            ) {
                if (response.isSuccessful) {
                    responseData.postValue(response.body())
                }
            }
        })

        return responseData
    }

    private fun <T> createApi(
        baseUrl: String,
        serviceClass: Class<T>
    ): T {
        if (baseUrl.isBlank()) {
            throw IllegalArgumentException()
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }
}
