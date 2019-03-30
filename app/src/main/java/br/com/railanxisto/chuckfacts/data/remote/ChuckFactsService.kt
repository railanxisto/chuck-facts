package br.com.railanxisto.chuckfacts.data.remote

import br.com.railanxisto.chuckfacts.data.remote.config.RetrofitManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ChuckFactsService {

    companion object {
        fun create(): ChuckFactsService {
            val retrofit = RetrofitManager()
            return retrofit.retrofitInstance().create(ChuckFactsService::class.java)
        }
    }

    @GET("jokes/categories")
    fun getCategories(): Call<List<String>>
}