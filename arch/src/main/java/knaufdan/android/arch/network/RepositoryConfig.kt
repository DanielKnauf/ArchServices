package knaufdan.android.arch.network

import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

data class RepositoryConfig(
    val baseUrl: String,
    val converterFactory: Converter.Factory = GsonConverterFactory.create()
)
