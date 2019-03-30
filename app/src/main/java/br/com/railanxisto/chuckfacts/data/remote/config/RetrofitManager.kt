package br.com.railanxisto.chuckfacts.data.remote.config

import br.com.railanxisto.chuckfacts.data.remote.ApiConstants
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.core.inject


class RetrofitManager : KoinComponent {

    val httpClient: OkHttpClient by inject()

    fun retrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }
}