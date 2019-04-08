package br.com.railanxisto.chuckfacts.data.remote.config

import br.com.railanxisto.chuckfacts.data.remote.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

interface HttpClient {
    fun create(): OkHttpClient
}

class HttpClientImpl : HttpClient {

    override fun create(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(ApiConstants.TIMEOUT_CONNECTION, TimeUnit.SECONDS)
            .readTimeout(ApiConstants.READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(ApiConstants.READ_WRITE_TIMEOUT, TimeUnit.SECONDS)

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(httpLoggingInterceptor)

        return client.build()
    }
}