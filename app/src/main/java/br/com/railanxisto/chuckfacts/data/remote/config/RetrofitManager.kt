package br.com.railanxisto.chuckfacts.data.remote.config

import br.com.railanxisto.chuckfacts.data.remote.ApiConstants
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.core.inject
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class RetrofitManager : KoinComponent {

    val httpClient: OkHttpClient by inject()

    fun retrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }
}